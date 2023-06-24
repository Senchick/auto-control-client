package com.company.autocontrol.di

import com.company.autocontrol.BuildConfig
import com.company.autocontrol.data.model.user.Role
import com.company.autocontrol.data.service.BookingService
import com.company.autocontrol.data.service.RoadSectionService
import com.company.autocontrol.data.service.UserService
import com.company.autocontrol.di.interceptor.AuthInterceptor
import com.company.autocontrol.di.interceptor.ErrorInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonSerializer<LocalDateTime> { src, _, _ ->
                    if (src == null) {
                        null
                    } else {
                        JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                    }
                }
            )
            .registerTypeAdapter(
                LocalDateTime::class.java,
                JsonDeserializer { json, _, _ ->
                    if (json == null) {
                        null
                    } else {
                        LocalDateTime.parse(json.asString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    }
                }
            )
            .registerTypeAdapter(
                Role::class.java,
                JsonDeserializer { json, _, _ ->
                    Role.values().find { it.ordinal == json.asJsonPrimitive.asInt }
                }
            )
            .create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor, errorInterceptor: ErrorInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            // .addInterceptor(errorInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Singleton
    @Provides
    fun provideRoadSectionService(retrofit: Retrofit): RoadSectionService {
        return retrofit.create(RoadSectionService::class.java)
    }

    @Singleton
    @Provides
    fun provideBookingService(retrofit: Retrofit): BookingService {
        return retrofit.create(BookingService::class.java)
    }
}
