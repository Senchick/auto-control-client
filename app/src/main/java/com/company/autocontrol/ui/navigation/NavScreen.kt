package com.company.autocontrol.ui.navigation

sealed class NavScreen(val route: String) {
    object Splash : NavScreen("splash")
    object Login : NavScreen("login")
    object Home : NavScreen("home")
    object Admin : NavScreen("admin")
}
