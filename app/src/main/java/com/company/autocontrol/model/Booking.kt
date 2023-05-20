package com.company.autocontrol.model

import androidx.annotation.Size

data class Booking(
    val bookingType: BookingType,
    @Size(value = 5)
    val time: String,
    val comment: String = "",
    val author: String = "",

)
