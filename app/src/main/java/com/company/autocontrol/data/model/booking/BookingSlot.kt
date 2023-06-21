package com.company.autocontrol.data.model.booking

import java.time.LocalTime

data class BookingSlot(
    val bookingType: BookingType,
    val time: LocalTime,
    val comment: String = "",
    val author: String = "",
    val bookingStatus: BookingStatus = BookingStatus.IDLE,
    val department: String = ""
)
