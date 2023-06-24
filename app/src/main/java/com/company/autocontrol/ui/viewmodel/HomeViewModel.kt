package com.company.autocontrol.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.autocontrol.data.model.booking.RoadSection
import com.company.autocontrol.data.model.error.ErrorResponse
import com.company.autocontrol.data.repository.UserRepository
import com.company.autocontrol.ui.state.HomeState
import com.company.autocontrol.ui.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState.Error(error = ErrorResponse<Nothing>("")))
    private val _homeUiState = MutableStateFlow(HomeUiState(null, null))
    val homeState: StateFlow<HomeState> = _homeState
    val homeUiState: StateFlow<HomeUiState> = _homeUiState

    init {
        loadState()
    }

    fun updateSelectedRoadSection(roadSection: RoadSection) {
        _homeUiState.update {
            it.copy(
                selectedRoadSection = roadSection
            )
        }
    }

    fun updateSelectedDate(localDate: LocalDate) {
        _homeUiState.update {
            it.copy(selectedDate = localDate)
        }
    }

    fun loadState() {
        viewModelScope.launch {
            _homeState.value = HomeState.Loading
        }
    }
}
