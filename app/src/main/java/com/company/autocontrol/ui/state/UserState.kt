package com.company.autocontrol.ui.state

import com.company.autocontrol.data.model.error.ErrorResponse
import com.company.autocontrol.data.model.user.UserLogin

sealed class UserState {
    object Idle : UserState()
    object Loading : UserState()
    data class Success(val userLogin: UserLogin) : UserState()
    data class Error(val error: ErrorResponse<*>) : UserState()
}
