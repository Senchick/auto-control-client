package com.company.autocontrol.ui.state

import com.company.autocontrol.data.model.booking.RoadSection
import java.time.LocalDate

data class HomeUiState(
    val selectedRoadSection: RoadSection?,
    val selectedDate: LocalDate?
)
