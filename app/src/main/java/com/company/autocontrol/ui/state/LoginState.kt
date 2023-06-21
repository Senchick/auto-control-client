package com.company.autocontrol.ui.state

import com.company.autocontrol.data.model.error.ErrorResponse

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val error: ErrorResponse<*>?) : LoginState()
}
