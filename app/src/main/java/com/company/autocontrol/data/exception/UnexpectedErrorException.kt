package com.company.autocontrol.data.exception

import com.company.autocontrol.data.model.error.ErrorResponse

class UnexpectedErrorException(errorResponse: ErrorResponse<*>) : ErrorException(errorResponse)