package com.company.autocontrol.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.autocontrol.data.exception.RoadSectionNotFoundException
import com.company.autocontrol.data.exception.RoleNotFoundException
import com.company.autocontrol.data.model.booking.Booking
import com.company.autocontrol.data.model.booking.BookingDto
import com.company.autocontrol.data.model.booking.RoadSection
import com.company.autocontrol.data.repository.BookingRepository
import com.company.autocontrol.data.repository.RoadSectionRepository
import com.company.autocontrol.data.repository.UserRepository
import com.company.autocontrol.ui.state.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val roadSectionRepository: RoadSectionRepository,
    private val bookingRepository: BookingRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeState>(HomeState.Loading)
    val homeState: StateFlow<HomeState> = _homeState

    init {
        viewModelScope.launch {
            while (true) {
                if (_homeState.value is HomeState.Success) {
                    val s = _homeState.value as HomeState.Success

                    _homeState.emit(s.updateState())
                }
                delay(5000)
            }
        }

        loadState()
    }

    fun updateSelectedRoadSection(roadSection: RoadSection) {
        viewModelScope.launch {
            val value = _homeState.value
            if (value is HomeState.Success) {
                val b = value.copy(selectedRoadSection = roadSection)
                _homeState.emit(b.updateState())
            }
        }
    }

    fun updateSelectedDate(date: LocalDateTime) {
        viewModelScope.launch {
            val value = _homeState.value
            if (value is HomeState.Success) {
                val b = value.copy(selectedDate = date)
                _homeState.emit(b.updateState())
            }
        }
    }

    private suspend fun HomeState.Success.updateState(): HomeState.Success {
        val bookings = bookingRepository.getAll(
            selectedDate,
            selectedRoadSection.id
        )

        return copy(bookings = bookings)
    }

    fun loadState() {
        viewModelScope.launch {
            _homeState.emit(HomeState.Loading)

            // TODO config

            try {
                val roadSections = roadSectionRepository.getAll()
                val roadSection = roadSections.firstOrNull() ?: throw RoadSectionNotFoundException()
                val bookings = bookingRepository.getAll(
                    LocalDateTime.now(),
                    roadSection.id
                )
                val role = userRepository.getRole() ?: throw RoleNotFoundException()

                _homeState.emit(
                    HomeState.Success(
                        role = role,
                        bookings = bookings,
                        roadSections = roadSections,
                        selectedDate = LocalDateTime.now(),
                        selectedRoadSection = roadSection
                    )
                )
            } catch (e: RoadSectionNotFoundException) {
                _homeState.emit(HomeState.Error(e))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addBooking(booking: Booking) {
        viewModelScope.launch {
            val value = _homeState.value
            if (value is HomeState.Success) {
                val b = value.copy()
                _homeState.emit(HomeState.Loading)
                bookingRepository.addBooking(
                    booking.let {
                        BookingDto(
                            date = it.date,
                            from = it.from,
                            to = it.to,
                            roadSectionId = it.roadSectionId,
                            bookingType = it.bookingType,
                            comment = it.comment
                        )
                    }
                )

                _homeState.emit(b.updateState())
            }
        }
    }
}
