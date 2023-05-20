package com.company.autocontrol.model

enum class BookingType {
    GENERAL, // сколько угодно участников
    MONO, // один участник
    CLOSE, // нельзя делать бронь
    NO_BOOKING;
}
