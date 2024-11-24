package com.openclassrooms.hexagonal.games.connection.connectionHome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.openclassrooms.hexagonal.games.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeConnection(
    modifier: Modifier = Modifier,
    onclickToLogin: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(id = R.string.sign_in))
                }
            )
        }
    ) {
        contentPadding ->
        ConnectionPage(
            modifier = Modifier.padding(contentPadding),
            onclickToLogin = onclickToLogin
        )
    }
}


@Composable
fun ConnectionPage(
    modifier: Modifier = Modifier,
    onclickToLogin: () -> Unit
) {


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()

    ) {


        Image(
            painter = painterResource(id = R.drawable.p13icon),
            contentDescription = null,
            modifier = Modifier
                .sizeIn(75.dp, 75.dp)
        )

        Spacer(Modifier.heightIn(80.dp))


        Button(onClick =  onclickToLogin ) {
            Text(text = stringResource(id = R.string.sign_with_email))

        }

    }


}

@Preview
@Composable
fun HomeConnectionPreview() {
    HomeConnection(
        onclickToLogin = {}
    )
}