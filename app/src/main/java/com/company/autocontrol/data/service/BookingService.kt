package com.company.autocontrol.data.service

import com.company.autocontrol.data.model.booking.BookingOverview
import retrofit2.Call
import retrofit2.http.GET

interface BookingService {
    @GET("/v1/user/booking/all")
    fun getAll(): Call<List<BookingOverview>>
}
