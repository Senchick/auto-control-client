package com.company.autocontrol.ui.viewmodel

import com.company.autocontrol.data.model.user.User

sealed class UserState {
    object Idle : UserState()
    object Loading : UserState()
    data class Success(val user: User) : UserState()
    data class Error(val error: Throwable) : UserState()
}
