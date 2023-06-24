package com.company.autocontrol.ui.state

import com.company.autocontrol.data.model.booking.BookingOverview
import com.company.autocontrol.data.model.booking.RoadSection
import com.company.autocontrol.data.model.error.ErrorResponse
import com.company.autocontrol.data.model.user.Role

sealed class HomeState {
    object Loading : HomeState()
    data class Success(
        val role: Role,
        val bookings: List<BookingOverview>,
        val roadSections: List<RoadSection>
    ) : HomeState()
    data class Error(val error: ErrorResponse<*>) : HomeState()
}
