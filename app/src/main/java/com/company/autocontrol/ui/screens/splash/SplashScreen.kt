package com.company.autocontrol.ui.screens.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.company.autocontrol.R
import com.company.autocontrol.ui.state.SplashState
import com.company.autocontrol.ui.viewmodel.SplashViewModel

@Composable
fun SplashScreen(navController: NavHostController, viewModel: SplashViewModel = hiltViewModel()) {
    val state = viewModel.splashState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = state.value) {
        if (state.value is SplashState.Success) {
            navController.navigate((state.value as SplashState.Success).navScreen.route) {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(R.string.app_name), fontSize = 48.sp)
            CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
        }
    }
}
