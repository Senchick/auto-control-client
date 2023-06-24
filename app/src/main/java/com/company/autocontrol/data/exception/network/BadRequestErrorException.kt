package com.company.autocontrol.data.exception.network

import com.company.autocontrol.data.model.error.ErrorResponse

class BadRequestErrorException(errorResponse: ErrorResponse<*>) : ErrorException(errorResponse)
