package com.company.autocontrol.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.company.autocontrol.ui.screens.home.HomeScreen
import com.company.autocontrol.ui.screens.login.LoginScreen
import com.company.autocontrol.ui.screens.splash.SplashScreen

@Composable
fun AutoControlNavHost(navController: NavHostController) {
    NavHost(
        navController,
        startDestination = NavScreen.Splash.route
    ) {
        composable(NavScreen.Splash.route) { SplashScreen(navController) }
        composable(NavScreen.Login.route) { LoginScreen(navController) }
        composable(NavScreen.Home.route) { HomeScreen() }
    }
}
