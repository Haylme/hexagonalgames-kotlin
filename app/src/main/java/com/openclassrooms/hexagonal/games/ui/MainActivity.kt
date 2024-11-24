package com.openclassrooms.hexagonal.games.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.openclassrooms.hexagonal.games.connection.connectionHome.HomeConnection
import com.openclassrooms.hexagonal.games.connection.login.LoginAccess
import com.openclassrooms.hexagonal.games.connection.newAccount.NewAccount
import com.openclassrooms.hexagonal.games.connection.passwordRecovery.RecoveryTopBar
import com.openclassrooms.hexagonal.games.screen.Screen
import com.openclassrooms.hexagonal.games.screen.ad.AddScreen
import com.openclassrooms.hexagonal.games.screen.homefeed.HomefeedScreen
import com.openclassrooms.hexagonal.games.screen.settings.SettingsScreen
import com.openclassrooms.hexagonal.games.ui.theme.HexagonalGamesTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity for the application. This activity serves as the entry point and container for the navigation
 * fragment. It handles setting up the toolbar, navigation controller, and action bar behavior.
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    /** private val auth_request_code = 125
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var listener: FirebaseAuth.AuthStateListener
    private lateinit var providers: List<AuthUI.IdpConfig>


    private fun init() {

    providers = arrayListOf(

    AuthUI.IdpConfig.EmailBuilder().build(),


    )

    firebaseAuth = FirebaseAuth.getInstance()
    listener = FirebaseAuth.AuthStateListener { p0 ->
    val user = p0.currentUser
    if (user != null)

    startSignInActivity()
    }
    }

    private fun startSignInActivity() {

    val signInIntent = AuthUI.getInstance()
    .createSignInIntentBuilder()
    .setTheme(R.style.Theme_HexagonalGames)
    .setAvailableProviders(providers)
    .setIsSmartLockEnabled(false, true)
    .build()
    startActivityForResult(signInIntent, auth_request_code)

    }**/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /** init()
        firebaseAuth.addAuthStateListener(listener)

        if (listener != null) {
        firebaseAuth.removeAuthStateListener(listener)
        }**/


        setContent {


            val navController = rememberNavController()

            HexagonalGamesTheme {
                HexagonalGamesNavHost(navHostController = navController)
            }
        }
    }

}


@Composable
fun HexagonalGamesNavHost(navHostController: NavHostController) {
    val snackbarHostState = remember { SnackbarHostState() }

    NavHost(
        navController = navHostController,
        startDestination = Screen.HomeConnection.route
    ) {
        composable(route = Screen.HomeConnection.route) {

            HomeConnection(
                onclickToLogin = {
                    navHostController.navigate(Screen.Login.route)
                }
            )
        }

        composable(route = Screen.Login.route) {

            LoginAccess(
                navController = navHostController,
                snackbarHostState = snackbarHostState
            )


        }

        composable(route = Screen.Recovery.route) {

            RecoveryTopBar(
                navController = navHostController,
                onclickBack =  {navHostController.navigateUp()}
            )
        }


        composable(route = Screen.CreateAccount.route) {

            NewAccount(
                navController = navHostController,
                onclickBack =  {navHostController.navigateUp()})

        }



        composable(route = Screen.Homefeed.route) {
            HomefeedScreen(
                onPostClick = {
                    //TODO
                },
                onSettingsClick = {
                    navHostController.navigate(Screen.Settings.route)
                },
                onFABClick = {
                    navHostController.navigate(Screen.AddPost.route)
                }
            )
        }
        composable(route = Screen.AddPost.route) {
            AddScreen(
                onBackClick = { navHostController.navigateUp() },
                onSaveClick = { navHostController.navigateUp() }
            )
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onBackClick = { navHostController.navigateUp() }
            )
        }
    }
}
