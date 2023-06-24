package com.company.autocontrol.data.repository

import com.company.autocontrol.data.model.booking.BookingOverview
import com.company.autocontrol.data.service.BookingService
import com.company.autocontrol.data.util.awaitResponseWithExceptionThrowing
import javax.inject.Inject
import javax.inject.Provider

class BookingRepository @Inject constructor(private val bookingService: Provider<BookingService>) {
    suspend fun getAll(): List<BookingOverview> {
        return bookingService.get().getAll().awaitResponseWithExceptionThrowing()!!
    }
}
