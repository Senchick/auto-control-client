package com.company.autocontrol.ui.viewmodel

import com.company.autocontrol.model.Booking
import com.company.autocontrol.model.RoadSection

data class BookingUiState(
    val bookings: List<Booking>,
    val roadSections: List<RoadSection>
)
