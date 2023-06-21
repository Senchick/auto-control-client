package com.company.autocontrol.data.model.booking

import com.company.autocontrol.data.model.user.User
import com.company.autocontrol.util.generateTimes
import java.time.LocalDateTime

data class BookingOverview(
    val author: User,
    val bookingType: BookingType,
    val fromInterval: LocalDateTime,
    val toInterval: LocalDateTime,
    val comment: String,
    val bookingStatus: BookingStatus,
    val createdDate: LocalDateTime,
)

fun List<BookingOverview>.asBookingSlots(): List<BookingSlot> {
    val bookingSlots = mutableListOf<BookingSlot>()
    val times = generateTimes()

    for (time in times) {
        val matchingBooking = find { booking ->
            val bookingStartTime = booking.fromInterval.toLocalTime()
            val bookingEndTime = booking.toInterval.toLocalTime()

            (time.isAfter(bookingStartTime) || time == bookingStartTime) && (
                time.plusMinutes(30).isBefore(
                    bookingEndTime
                ) || time.plusMinutes(30).equals(bookingEndTime)
                )
        }

        if (matchingBooking != null) {
            bookingSlots.add(BookingSlot(matchingBooking.bookingType, time))
        } else {
            bookingSlots.add(BookingSlot(BookingType.NO_BOOKING, time))
        }
    }

    return bookingSlots
}
