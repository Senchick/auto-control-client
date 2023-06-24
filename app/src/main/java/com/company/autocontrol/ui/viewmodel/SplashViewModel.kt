package com.company.autocontrol.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.autocontrol.data.exception.network.UnauthorizedErrorException
import com.company.autocontrol.data.exception.network.UnexpectedErrorException
import com.company.autocontrol.data.repository.UserRepository
import com.company.autocontrol.ui.navigation.NavScreen
import com.company.autocontrol.ui.state.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _splashState = MutableStateFlow<SplashState>(SplashState.Loading)
    val splashState: StateFlow<SplashState> = _splashState
    init {
        viewModelScope.launch {
            val user = userRepository.getUser(true)
            if (user == null) {
                _splashState.value = SplashState.Success(NavScreen.Login)
            } else {
                try {
                    userRepository.getInfo()
                    _splashState.value = SplashState.Success(NavScreen.Home)
                } catch (e: UnauthorizedErrorException) {
                    _splashState.value = SplashState.Success(NavScreen.Login)
                } catch (e: UnexpectedErrorException) {
                    _splashState.value = SplashState.Success(NavScreen.Login)
                }
            }
        }
    }
}
