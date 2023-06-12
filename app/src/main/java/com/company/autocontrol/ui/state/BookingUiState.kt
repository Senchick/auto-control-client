package com.company.autocontrol.ui.state

import com.company.autocontrol.data.model.booking.Booking
import com.company.autocontrol.data.model.booking.RoadSection
import java.time.LocalDate

data class BookingUiState(
    val bookings: List<Booking>,
    val roadSections: List<RoadSection>,
    val selectedRoadSection: RoadSection? = null, // TODO избавиться от NULL
    val selectedDate: LocalDate = LocalDate.now()
)
