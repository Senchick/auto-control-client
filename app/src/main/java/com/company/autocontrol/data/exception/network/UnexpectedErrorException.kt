package com.company.autocontrol.data.exception.network

import com.company.autocontrol.data.model.error.ErrorResponse

class UnexpectedErrorException(errorResponse: ErrorResponse<*>) : ErrorException(errorResponse)