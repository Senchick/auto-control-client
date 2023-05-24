package com.company.autocontrol.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.company.autocontrol.model.Booking
import com.company.autocontrol.model.BookingType
import com.company.autocontrol.model.RoadSection
import com.company.autocontrol.util.generateDates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor() : ViewModel() {

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
