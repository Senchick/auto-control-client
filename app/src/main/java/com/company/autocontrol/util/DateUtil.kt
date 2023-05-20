package com.company.autocontrol.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun generateDates(): Array<String> {
    val m = Array(35) { "" }
    var i = 0

    for (hour in 6..23) {
        for (minute in listOf(0, 30)) {
            if (i != 35) {
                m[i++] = String.format(locale = Locale.getDefault(), "%02d:%02d", hour, minute)
            }
        }
    }

    return m
}

fun LocalDate.formatDate(): String? {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    return format(formatter)
}
