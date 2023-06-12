package com.company.autocontrol.di

import com.company.autocontrol.BuildConfig
import com.company.autocontrol.data.repository.UserRepository
import com.company.autocontrol.data.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(userRepository: UserRepository): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
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

                chain.proceed(request)
            }
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}
