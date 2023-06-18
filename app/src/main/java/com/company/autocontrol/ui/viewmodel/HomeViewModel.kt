package com.company.autocontrol.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.company.autocontrol.data.model.booking.Booking
import com.company.autocontrol.data.model.booking.BookingType
import com.company.autocontrol.data.model.booking.RoadSection
import com.company.autocontrol.ui.state.BookingUiState
import com.company.autocontrol.util.generateDates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _bookingUiState = MutableStateFlow(
        BookingUiState(
            generateDates().mapIndexed { index, s ->
                Booking(BookingType.values()[index % 4], s)
            },
            (0..100).map { RoadSection("Дорога $it") }
        )
    )

    val bookingUiState: StateFlow<BookingUiState> = _bookingUiState

    fun updateSelectedRoadSection(roadSection: RoadSection) {
        _bookingUiState.update {
            it.copy(selectedRoadSection = roadSection)
        }
    }

    fun updateSelectedDate(localDate: LocalDate) {
        _bookingUiState.update {
            it.copy(selectedDate = localDate)
        }
    }
}
