package com.company.autocontrol.data.exception

import com.company.autocontrol.data.model.error.ErrorResponse

abstract class ErrorException(val errorResponse: ErrorResponse<*>) : Exception(errorResponse.toString())
