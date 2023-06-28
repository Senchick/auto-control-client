package com.company.autocontrol.data.service

import com.company.autocontrol.data.model.booking.Booking
import com.company.autocontrol.data.model.booking.BookingDto
import com.company.autocontrol.data.model.booking.IdDto
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalDateTime

interface BookingService {
    @GET("/v1/user/booking/all")
    fun getAll(
        @Query("date") date: LocalDateTime,
        @Query("roadSectionId") roadSectionId: Long
    ): Call<List<Booking>>

    @POST("/v1/user/booking/add")
    fun add(@Body booking: BookingDto): Call<IdDto>

    @GET("/v1/moder/booking/{id}/approve")
    fun approveBooking(@Path("id") id: Long): Call<*>

    @GET("/v1/moder/booking/{id}/disapprove")
    fun disapproveBooking(@Path("id") id: Long): Call<*>
}
