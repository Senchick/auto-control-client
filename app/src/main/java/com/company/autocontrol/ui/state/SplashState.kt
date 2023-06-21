package com.company.autocontrol.ui.state

import com.company.autocontrol.ui.navigation.NavScreen

sealed class SplashState {
    object Loading : SplashState()
    data class Success(val navScreen: NavScreen) : SplashState()
}
