package com.company.autocontrol.di.interceptor

import com.company.autocontrol.data.repository.UserRepository
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val userRepository: UserRepository) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val request = originalRequest.newBuilder()
            .header("Accept-Language", Locale.getDefault().language)
            .let {
                val user = userRepository.getUser()

                if (user != null) {
                    val credentials = Credentials.basic(user.login, user.password)

                    return@let it.header("Authorization", credentials)
                }

                it
            }
            .build()

        return chain.proceed(request)
    }
}