package com.company.autocontrol.data.model.booking

import com.company.autocontrol.data.model.user.User
import java.time.LocalDateTime
import java.time.LocalTime

data class BookingSlot(
    val time: LocalTime,
    val roadSection: RoadSection,
    val date: LocalDateTime,
    val booking: Booking? = null
) {
    val author: User?
        get() = booking?.author
    val bookingType: BookingType
        get() = booking?.bookingType ?: BookingType.NO_BOOKING
}
