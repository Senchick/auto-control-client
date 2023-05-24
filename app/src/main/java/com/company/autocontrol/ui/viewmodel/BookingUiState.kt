package com.company.autocontrol.ui.viewmodel

import com.company.autocontrol.model.Booking
import com.company.autocontrol.model.RoadSection
import java.time.LocalDate

data class BookingUiState(
    val bookings: List<Booking>,
    val roadSections: List<RoadSection>,
    val selectedRoadSection: RoadSection? = null, // TODO избавиться от NULL
    val selectedDate: LocalDate = LocalDate.now()
)
