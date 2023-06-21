package com.company.autocontrol.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.company.autocontrol.data.model.booking.RoadSection
import com.company.autocontrol.ui.state.HomeState
import com.company.autocontrol.util.updateWithState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState.Idle)
    val homeState: StateFlow<HomeState> = _homeState

    fun updateSelectedRoadSection(roadSection: RoadSection) {
        _homeState.updateWithState<HomeState, HomeState.Success> {
            it.copy(
                selectedRoadSection = roadSection
            )
        }
    }

    fun updateSelectedDate(localDate: LocalDate) {
        _homeState.updateWithState<HomeState, HomeState.Success> {
            it.copy(selectedDate = localDate)
        }
    }
}
