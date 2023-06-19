package com.company.autocontrol.data.model.booking

import com.company.autocontrol.data.model.user.User
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
