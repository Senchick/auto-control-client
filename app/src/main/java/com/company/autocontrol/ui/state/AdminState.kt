package com.company.autocontrol.ui.state

import com.company.autocontrol.data.model.error.ErrorResponse

sealed class AdminState {
    object Idle : AdminState()
    object Loading : AdminState()
    data class Success(val s: String) : AdminState()
    data class Error(val error: ErrorResponse<*>) : AdminState()
}
