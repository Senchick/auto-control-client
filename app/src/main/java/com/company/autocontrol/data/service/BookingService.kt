package com.company.autocontrol.data.service

import com.company.autocontrol.data.model.booking.Booking
import com.company.autocontrol.data.model.booking.BookingDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.time.LocalDateTime

interface BookingService {
    @GET("/v1/user/booking/all")
    fun getAll(
        @Query("date") date: LocalDateTime,
        @Query("roadSectionId") roadSectionId: Long
    ): Call<List<Booking>>

    @POST("/v1/user/booking/add")
    fun add(@Body booking: BookingDto): Call<Long>
}
