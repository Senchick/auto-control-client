package com.company.autocontrol.data.exception.network

import com.company.autocontrol.data.model.error.ErrorResponse

abstract class ErrorException(val errorResponse: ErrorResponse<*>) : Exception(errorResponse.toString())
