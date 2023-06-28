package com.company.autocontrol.data.repository

import com.company.autocontrol.data.model.booking.Booking
import com.company.autocontrol.data.model.booking.BookingDto
import com.company.autocontrol.data.service.BookingService
import com.company.autocontrol.data.util.awaitResponseWithExceptionThrowing
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Provider

class BookingRepository @Inject constructor(private val bookingService: Provider<BookingService>) {
    suspend fun getAll(date: LocalDateTime, roadSectionId: Long): List<Booking> {
        return bookingService.get().getAll(date, roadSectionId).awaitResponseWithExceptionThrowing()!!
    }
    suspend fun addBooking(bookingDto: BookingDto): Long {
        return bookingService.get().add(bookingDto).awaitResponseWithExceptionThrowing()!!.id
    }

    suspend fun approve(id: Long) {
        bookingService.get().approveBooking(id).awaitResponseWithExceptionThrowing()
    }

    suspend fun disapprove(id: Long) {
        bookingService.get().disapproveBooking(id).awaitResponseWithExceptionThrowing()
    }
}
