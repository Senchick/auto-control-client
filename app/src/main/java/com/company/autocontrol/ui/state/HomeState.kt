package com.company.autocontrol.ui.state

import com.company.autocontrol.data.model.booking.Booking
import com.company.autocontrol.data.model.booking.RoadSection
import com.company.autocontrol.data.model.user.Role
import java.time.LocalDateTime

sealed class HomeState {
    object Loading : HomeState()
    data class Success(
        val role: Role,
        val bookings: List<Booking>,
        val roadSections: List<RoadSection>,
        val selectedRoadSection: RoadSection,
        val selectedDate: LocalDateTime
    ) : HomeState()
    data class Error(val error: Throwable) : HomeState()
}
