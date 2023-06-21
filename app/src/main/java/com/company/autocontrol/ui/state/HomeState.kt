package com.company.autocontrol.ui.state

import com.company.autocontrol.data.model.booking.BookingOverview
import com.company.autocontrol.data.model.booking.RoadSection
import com.company.autocontrol.data.model.error.ErrorResponse
import java.time.LocalDate

sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()
    data class Success(
        val bookings: List<BookingOverview>,
        val roadSections: List<RoadSection>,
        val selectedRoadSection: RoadSection,
        val selectedDate: LocalDate
    ) : HomeState()
    data class Error(val error: ErrorResponse<*>) : HomeState()
}
