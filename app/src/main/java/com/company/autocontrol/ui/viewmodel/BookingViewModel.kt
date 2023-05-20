package com.company.autocontrol.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.company.autocontrol.model.Booking
import com.company.autocontrol.model.BookingType
import com.company.autocontrol.util.generateDates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor() : ViewModel() {

    private val _bookingUiState = MutableStateFlow(
        BookingUiState(
            generateDates().mapIndexed { index, s ->
                Booking(BookingType.values()[index % 4], s)
            },
            listOf()
        )
    )

    val bookingUiState: StateFlow<BookingUiState> = _bookingUiState
}
