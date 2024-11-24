package com.openclassrooms.hexagonal.games.connection.passwordRecovery

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.openclassrooms.hexagonal.games.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoveryTopBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    onclickBack: () -> Unit,

    ) {

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {},

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


        ) {


            contentPadding ->
        Recovery(
            modifier = Modifier.padding(contentPadding),
            navController = navController,


            )
    }


}

@Composable
fun Recovery(
    modifier: Modifier = Modifier,
    navController: NavController

) {


}