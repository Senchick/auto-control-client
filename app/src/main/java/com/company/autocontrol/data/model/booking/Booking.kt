package com.company.autocontrol.data.model.booking

import androidx.annotation.Size

data class Booking(
    val bookingType: BookingType,
    @Size(value = 5)
    val time: String,
    val comment: String = "",
    val author: String = "",
    val bookingStatus: BookingStatus = BookingStatus.IDLE,
    val department: String = ""
) {
}
