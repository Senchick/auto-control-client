package com.company.autocontrol.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.company.autocontrol.R
import com.company.autocontrol.ui.navigation.NavScreen
import com.company.autocontrol.ui.state.LoginState
import com.company.autocontrol.ui.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var enabledButton by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 48.dp),
            text = stringResource(R.string.app_name),
            fontSize = 48.sp
        )
        OutlinedTextField(
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "emailIcon") },
            value = login,
            singleLine = true,
            onValueChange = { login = it },
            label = { Text("Login") }
        )
        OutlinedTextField(
            leadingIcon = { Icon(imageVector = Icons.Default.Key, contentDescription = "emailIcon") },
            modifier = Modifier.padding(bottom = 32.dp),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                viewModel.login(login, password)
            }),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
        )
        Button(
            onClick = {
                viewModel.login(login, password)
            },
            enabled = enabledButton
        ) {
            Text("Login")
        }

        when (state) {
            LoginState.Idle -> {
                enabledButton = true
            }

            is LoginState.Error -> {
                enabledButton = true
            }

            LoginState.Loading -> {
                enabledButton = false
            }
            LoginState.Success -> {
                LaunchedEffect(key1 = state) {
                    navController.navigate(NavScreen.Home.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            }
        }
    }
}
