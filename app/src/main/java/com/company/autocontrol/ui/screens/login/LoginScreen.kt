package com.company.autocontrol.ui.screens.login

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.company.autocontrol.ui.navigation.NavScreen
import com.company.autocontrol.ui.state.UserState
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
    Row {


        when (state) {
            UserState.Idle -> {
                TextField(
                    value = login,
                    onValueChange = { login = it },
                    label = { Text("Login") }
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation()
                )
                Button(onClick = {
                    viewModel

                }) {
                    Text("Login")
                }
            }

            is UserState.Error -> TODO()

            UserState.Loading -> TODO()
            is UserState.Success -> navController.navigate(NavScreen.Home.route)
        }
    }
}
