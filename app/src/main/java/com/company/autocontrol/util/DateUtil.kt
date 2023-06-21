package com.company.autocontrol.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun generateTimes(): Array<LocalTime> {
    val m = Array(35) { LocalTime.now() }
    var i = 0

    for (hour in 6..23) {
        for (minute in listOf(0, 30)) {
            if (i != 35) {
                m[i++] = LocalTime.of(hour, minute)
            }
        }
    }

    return m
}

fun LocalDate.formatDate(): String? {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    return format(formatter)
}

fun LocalTime.formatTime(): String? {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    return format(formatter)
}
