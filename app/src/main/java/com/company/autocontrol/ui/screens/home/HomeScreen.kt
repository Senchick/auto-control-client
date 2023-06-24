package com.company.autocontrol.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.EditRoad
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.company.autocontrol.data.model.booking.BookingSlot
import com.company.autocontrol.data.model.booking.asBookingSlots
import com.company.autocontrol.ui.state.HomeState
import com.company.autocontrol.ui.viewmodel.HomeViewModel
import com.company.autocontrol.util.formatDate
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.valentinilk.shimmer.shimmer
import java.time.LocalTime
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.homeState.collectAsStateWithLifecycle()
    val calendarState = rememberUseCaseState()
    val showRoadSectionDialog = remember { mutableStateOf(false) }
    val showOverviewDialog = remember { mutableStateOf(false) }

    when (state) {
        is HomeState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Произошла ошибка: ${(state as HomeState.Error)
                            .error
                            .message
                            .takeIf { !it.isNullOrEmpty() } ?: "Неизвестная ошибка"}",
                    )
                    Button(onClick = { viewModel.loadState() }, modifier = Modifier.padding(8.dp)) {
                        Text("Обновить")
                    }
                }
            }
        }
        HomeState.Loading -> {
            Column(modifier = Modifier.padding(12.dp).shimmer()) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                            .fillMaxWidth()
                            .height(72.dp)
                    ) {}
                }

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(16.dp))

                ) {
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                            .fillMaxWidth()
                            .height(72.dp)
                    ) {}
                }

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .padding(top = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Box(
                        modifier = Modifier.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                            .fillMaxSize()
                    ) {}
                }
            }
        }
        is HomeState.Success -> {
            val successState = state as HomeState.Success
            val bookingOverviewDialog = remember {
                mutableStateOf(
                    BookingSlot(LocalTime.now(), successState.selectedRoadSection, successState.selectedDate)
                )
            }
            CalendarDialog(
                state = calendarState,
                config = CalendarConfig(
                    locale = Locale.getDefault(),
                    style = CalendarStyle.MONTH,
                    yearSelection = true,
                    monthSelection = true
                ),
                selection = CalendarSelection.Date {
                    viewModel.updateSelectedDate(it.atStartOfDay())
                }
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { calendarState.show() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            Icons.Rounded.DateRange,
                            contentDescription = "",
                            modifier = Modifier.padding(16.dp)
                        )
                        Column {
                            Text(
                                text = "Выбранная дата: ${successState.selectedDate.formatDate()}"
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { showRoadSectionDialog.value = true }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            Icons.Rounded.EditRoad,
                            contentDescription = "",
                            modifier = Modifier.padding(16.dp)
                        )
                        Column {
                            Text("Выбранный участок дороги: ${successState.selectedRoadSection.name}")
                        }
                    }
                }

                RoadSectionDialog((state as HomeState.Success).roadSections, showRoadSectionDialog) {
                    viewModel.updateSelectedRoadSection(it)
                }

                BookingOverviewDialog(bookingOverviewDialog, showOverviewDialog, successState.role) {
                    viewModel.addBooking(it)
                }

                LazyVerticalGrid(
                    modifier = Modifier.padding(top = 8.dp),
                    columns = GridCells.FixedSize(72.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    items(
                        successState.bookings.asBookingSlots(
                            successState.selectedRoadSection,
                            successState.selectedDate
                        )
                    ) { bookingSlot ->

                        BookingItem(bookingSlot) {
                            bookingOverviewDialog.value = bookingSlot
                            showOverviewDialog.value = true
                        }
                    }
                }
            }
        }
    }
}
