package com.company.autocontrol.di.interceptor

import com.company.autocontrol.data.exception.BadRequestErrorException
import com.company.autocontrol.data.exception.UnauthorizedErrorException
import com.company.autocontrol.data.exception.UnexpectedErrorException
import com.company.autocontrol.data.model.error.ErrorResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

class ErrorInterceptor @Inject constructor(private val gson: Gson) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (!response.isSuccessful) {
            val errorResponse = parseError(response)

            when (response.code) {
                HttpURLConnection.HTTP_BAD_REQUEST -> throw BadRequestErrorException(errorResponse)
                HttpURLConnection.HTTP_UNAUTHORIZED -> throw UnauthorizedErrorException(errorResponse)
                else -> throw UnexpectedErrorException(errorResponse)
            }
        }

        return response
    }

    private fun parseError(response: Response): ErrorResponse<*> {
        val errorBody = response.body?.string()

        return gson.fromJson(errorBody, ErrorResponse::class.java)
    }
}
