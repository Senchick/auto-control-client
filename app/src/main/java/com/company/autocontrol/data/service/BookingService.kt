package com.company.autocontrol.data.service

import com.company.autocontrol.data.model.user.User
import retrofit2.http.GET

interface BookingService {
    @GET("/v1/user/booking/info")
    suspend fun getInfo(): User?
}