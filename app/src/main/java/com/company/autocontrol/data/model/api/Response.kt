package com.company.autocontrol.data.model.api

import com.company.autocontrol.data.exception.network.ErrorException

sealed class Response<out T> {
    data class Success<out T>(val data: T?) : Response<T>()
    data class Error(val error: ErrorException) : Response<Nothing>()
}
