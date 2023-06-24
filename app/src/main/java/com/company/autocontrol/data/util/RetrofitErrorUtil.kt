package com.company.autocontrol.data.util

import com.company.autocontrol.data.exception.network.BadRequestErrorException
import com.company.autocontrol.data.exception.network.ErrorException
import com.company.autocontrol.data.exception.network.UnauthorizedErrorException
import com.company.autocontrol.data.exception.network.UnexpectedErrorException
import com.company.autocontrol.data.model.api.Response
import com.company.autocontrol.data.model.error.ErrorResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.awaitResponse
import java.net.HttpURLConnection

suspend fun <T> Call<T>.awaitResponseWithErrorHandling(): Response<T> {
    return try {
        val response = awaitResponse()

        if (!response.isSuccessful) {
            val errorResponse = parseError(response)

            Response.Error(
                when (response.code()) {
                    HttpURLConnection.HTTP_BAD_REQUEST -> BadRequestErrorException(errorResponse)
                    HttpURLConnection.HTTP_UNAUTHORIZED -> UnauthorizedErrorException(errorResponse)
                    else -> UnexpectedErrorException(errorResponse)
                }
            )
        } else {
            Response.Success(response.body())
        }
    } catch (@Suppress("SwallowedException", "TooGenericExceptionCaught") e: Exception) {
        e.printStackTrace()
        Response.Error(UnexpectedErrorException(ErrorResponse<Nothing>("Network error")))
    }
}

@Throws(ErrorException::class)
suspend fun <T> Call<T>.awaitResponseWithExceptionThrowing(): T? {
    val response: retrofit2.Response<T>

    try {
        response = awaitResponse()
    } catch (e: Exception) {
        e.printStackTrace()
        throw UnexpectedErrorException(ErrorResponse<Nothing>("Unexpected Error"))
    }

    if (!response.isSuccessful) {
        val errorResponse = parseError(response)

        throw when (response.code()) {
            HttpURLConnection.HTTP_BAD_REQUEST -> BadRequestErrorException(errorResponse)
            HttpURLConnection.HTTP_UNAUTHORIZED -> UnauthorizedErrorException(errorResponse)
            else -> UnexpectedErrorException(errorResponse)
        }
    }

    return response.body()
}

private fun parseError(response: retrofit2.Response<*>): ErrorResponse<*> {
    val errorBody = response.errorBody()?.string()

    return Gson().fromJson(errorBody, ErrorResponse::class.java)
}
