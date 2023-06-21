package com.company.autocontrol.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.autocontrol.data.exception.UnauthorizedErrorException
import com.company.autocontrol.data.model.user.UserLogin
import com.company.autocontrol.data.repository.UserRepository
import com.company.autocontrol.ui.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> get() = _state

    fun login(login: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.emit(LoginState.Loading)
            try {
                userRepository.fetchUser(UserLogin(login, password))
                _state.emit(LoginState.Success)
            } catch (e: UnauthorizedErrorException) {
                e.printStackTrace()
                _state.emit(LoginState.Error(e.errorResponse))
            } catch (e: Exception) {
                e.printStackTrace()
                _state.emit(LoginState.Error(null))
            }
        }
    }
}
