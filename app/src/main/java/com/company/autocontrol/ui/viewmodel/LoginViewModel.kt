package com.company.autocontrol.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.autocontrol.data.repository.UserRepository
import com.company.autocontrol.ui.state.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val _state = MutableStateFlow<UserState>(UserState.Idle)
    val state: StateFlow<UserState> get() = _state

    init {
        viewModelScope.launch {
        }
    }
}
