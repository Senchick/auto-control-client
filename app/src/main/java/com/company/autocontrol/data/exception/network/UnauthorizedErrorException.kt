package com.company.autocontrol.data.exception.network

import com.company.autocontrol.data.model.error.ErrorResponse

class UnauthorizedErrorException(errorResponse: ErrorResponse<*>) : ErrorException(errorResponse)
