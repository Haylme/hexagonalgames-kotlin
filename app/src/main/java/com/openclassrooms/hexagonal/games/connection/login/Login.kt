package com.openclassrooms.hexagonal.games.connection.login

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.openclassrooms.hexagonal.games.R
import com.openclassrooms.hexagonal.games.screen.Screen
import kotlinx.coroutines.launch
import kotlin.math.sign


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginAccess(
    navController: NavController,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {


    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.sign_in))
                }
            )


        },


        snackbarHost = { SnackbarHost(snackbarHostState) },


        ) {


            contentPadding ->
        GetLogin(
            modifier = Modifier.padding(contentPadding),
            navController = navController,
            snackbarHostState = snackbarHostState

        )
    }


}

@Composable
fun GetLogin(
    navController: NavController,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {
    var textFieldInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    var signInMethods by remember { mutableStateOf<List<String>?>(null) }
    var showPasswordField by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val isPreview = LocalInspectionMode.current


    val auth: FirebaseAuth? = if (!isPreview) Firebase.auth else null

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = textFieldInput,
            onValueChange = { newText -> textFieldInput = newText },
            enabled = !showPasswordField,
            maxLines = 1,
            modifier = Modifier
                .widthIn(328.dp)
                .heightIn(53.dp),
            shape = RoundedCornerShape(18.dp),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.email),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black,
                    fontSize = 14.sp
                )
            },
            colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent)
        )
        if (!showPasswordField) {
            Text(
                text = stringResource(id = R.string.no_account),

                modifier = Modifier
                    .clickable {
                        navController.navigate(Screen.CreateAccount.route)
                    }

            )
        }

        Spacer(modifier = Modifier.height(80.dp))

        if (showPasswordField) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = passwordInput,
                    onValueChange = { newText -> passwordInput = newText },
                    label = { Text(text = stringResource(id = R.string.password)) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                )

                Checkbox(
                    checked = passwordVisible,
                    onCheckedChange = { passwordVisible = it }
                )
            }
            Row {
                Text(
                    text = stringResource(id = R.string.recovery),
                    modifier = Modifier.clickable {
                        navController.navigate("recovery")
                    }
                )
            }
        }


        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }

        if (!showPasswordField) {
            Button(
                onClick = {
                     if (!isPreview) {
                        auth?.fetchSignInMethodsForEmail(textFieldInput)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    signInMethods = task.result?.signInMethods
                                    if (!signInMethods.isNullOrEmpty()) {
                                        showPasswordField = true

                                                scope.launch {
                                                    val message = context.getString(R.string.mail_known, textFieldInput)
                                                    snackbarHostState.showSnackbar(message)
                                                }
                                    } else {
                                        if(signInMethods == null) {
                                            scope.launch {
                                                val message = context.getString(R.string.wrong_mail)
                                                snackbarHostState.showSnackbar(message)
                                            }
                                            textFieldInput = ""
                                        }else if(signInMethods!!.isEmpty()){
                                            scope.launch {
                                                val message = context.getString(R.string.empty_mail)
                                                snackbarHostState.showSnackbar(message)
                                            }
                                            textFieldInput = ""
                                        }
                                        else if (!isNetworkAvailable(context)) {

                                            scope.launch {
                                                val message = context.getString(R.string.no_internet)
                                                snackbarHostState.showSnackbar(message)
                                            }
                                            textFieldInput = ""
                                        }


                                    }
                                } else {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Error fetching sign-in methods")
                                    }
                                    textFieldInput = ""
                                    passwordInput = ""
                                }
                            }
                    }
                }) {
                Text("Next")
            }
        } else {
            Button(onClick = {
                if (!isPreview) {
                    auth?.signInWithEmailAndPassword(textFieldInput, passwordInput)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navController.navigate("homefeed")
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Incorrect password")
                                }
                            }
                        }
                }
            }) {
                Text(stringResource(id = R.string.next))
            }
        }
    }
}


@Preview
@Composable
fun LoginAccessPreview() {
    LoginAccess(
        navController = NavController(LocalContext.current),
        snackbarHostState = SnackbarHostState()
    )


}

