package com.company.autocontrol.data.model.booking

import com.company.autocontrol.data.model.user.User
import com.company.autocontrol.util.generateTimes
import java.time.LocalDateTime

data class Booking(
    val id: Long,
    val author: User,
    val bookingType: BookingType,
    val from: LocalDateTime,
    val to: LocalDateTime,
    val comment: String,
    val bookingStatus: BookingStatus,
    val date: LocalDateTime,
    val createdDate: LocalDateTime,
    val roadSectionId: Long
)

data class BookingDto(
    val date: LocalDateTime,
    val from: LocalDateTime,
    val to: LocalDateTime,
    val roadSectionId: Long,
    val bookingType: BookingType,
    val comment: String
)

data class BookingDialog(
    val date: LocalDateTime? = null,
    val from: LocalDateTime? = null,
    val to: LocalDateTime? = null,
    val roadSectionId: Long? = null,
    val bookingType: BookingType? = null,
    val comment: String? = null,
    val bookingStatus: BookingStatus? = null
)

fun List<Booking>.asBookingSlots(selectedRoadSection: RoadSection, selectedDate: LocalDateTime): List<BookingSlot> {
    val bookingSlots = mutableListOf<BookingSlot>()
    val times = generateTimes()

    for (time in times) {
        val matchingBooking = find { booking ->
            val bookingStartTime = booking.from.toLocalTime()
            val bookingEndTime = booking.to.toLocalTime()

            time.isAfter(bookingStartTime) &&
                time.isBefore(
                    bookingEndTime
                )
        }

        bookingSlots.add(
            BookingSlot(
                time = time,
                roadSection = selectedRoadSection,
                date = selectedDate,
                booking = matchingBooking
            )
        )
    }

    return bookingSlots
}
