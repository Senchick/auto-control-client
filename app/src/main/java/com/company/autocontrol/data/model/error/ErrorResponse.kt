package com.company.autocontrol.data.model.error

data class ErrorResponse<T>(
    val message: String? = null,
    val code: ErrorCode? = null,
    val data: T? = null
)
