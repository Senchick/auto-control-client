package com.company.autocontrol.data.exception

import com.company.autocontrol.data.model.error.ErrorResponse

class BadRequestErrorException(errorResponse: ErrorResponse<*>) : ErrorException(errorResponse)
