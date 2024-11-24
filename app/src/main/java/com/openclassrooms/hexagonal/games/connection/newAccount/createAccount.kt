package com.openclassrooms.hexagonal.games.connection.newAccount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.openclassrooms.hexagonal.games.R
import com.openclassrooms.hexagonal.games.connection.connectionHome.ConnectionPage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewAccount(
    modifier: Modifier = Modifier,
    navController: NavController,
    onclickBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.new_account))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onclickBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.contentDescription_go_back)
                        )
                    }
                }

            )


        }


    ) { contentPadding ->
        Account(
            modifier = Modifier.padding(contentPadding),
            navController = navController

        )
    }


}


@Composable
fun Account(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val emailInput = remember { mutableStateOf("") }
    val nameInput = remember { mutableStateOf("") }
    val passwordInput = remember { mutableStateOf("") }




    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = emailInput.value,
            onValueChange = { newText -> emailInput.value = newText },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nameInput.value,
            onValueChange = { newText -> nameInput.value = newText },
            placeholder = {
                Text(text = stringResource(id = R.string.name_and_surname))
            },
            label = { Text("Name") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = passwordInput.value,
            onValueChange = { newText -> passwordInput.value = newText },
            label = { Text("Password") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if (emailInput.value.isNotEmpty() && nameInput.value.isNotEmpty() && passwordInput.value.isNotEmpty()) {
                val auth = FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(emailInput.value, passwordInput.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val user = auth.currentUser
                            navController.navigate("homefeed")


                        } else {
                            // Handle failure
                            // Show error message
                        }
                    }
            } else {
                // Show error message for empty fields
            }
        }) {
            Text(stringResource(id = R.string.save))
        }
    }
}


@Preview
@Composable
fun NewAccountPreview() {
    NewAccount(
        navController = NavController(LocalContext.current),
        onclickBack = {})
}