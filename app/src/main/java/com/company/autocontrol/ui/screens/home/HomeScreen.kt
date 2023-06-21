package com.company.autocontrol.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.EditRoad
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.company.autocontrol.data.model.booking.BookingOverview
import com.company.autocontrol.data.model.booking.asBookingSlots
import com.company.autocontrol.ui.state.HomeState
import com.company.autocontrol.ui.viewmodel.HomeViewModel
import com.company.autocontrol.util.formatDate
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.homeState.collectAsStateWithLifecycle()
    val calendarState = rememberUseCaseState()
    val showRoadSectionDialog = remember { mutableStateOf(false) }
    val showOverviewDialog = remember { mutableStateOf(false) }
    val bookingOverviewDialog = remember { mutableStateOf<BookingOverview?>(null) }

    when (uiState) {
        HomeState.Idle -> Text("IDLE")
        HomeState.Loading -> Text("Loading")
        is HomeState.Error -> Text("Error: " + (uiState as HomeState.Error).error.toString())
        is HomeState.Success -> SuccessScreen(
            calendarState,
            viewModel,
            showRoadSectionDialog,
            uiState as HomeState.Success,
            bookingOverviewDialog,
            showOverviewDialog
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuccessScreen(
    calendarState: UseCaseState,
    viewModel: HomeViewModel,
    showRoadSectionDialog: MutableState<Boolean>,
    uiState: HomeState.Success,
    bookingOverviewDialog: MutableState<BookingOverview?>,
    showOverviewDialog: MutableState<Boolean>
) {
    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            locale = Locale.getDefault(),
            style = CalendarStyle.MONTH,
            yearSelection = true,
            monthSelection = true
        ),
        selection = CalendarSelection.Date {
            viewModel.updateSelectedDate(it)
        }
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Row {
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                Button(
                    onClick = { calendarState.show() },
                ) {
                    Icon(
                        Icons.Rounded.DateRange,
                        contentDescription = "",
                        modifier = Modifier.padding(end = 12.dp)
                    )

                    Text(text = "Выбрать дату")
                }
            }

            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                Button(
                    onClick = {
                        showRoadSectionDialog.value = true
                    },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Icon(
                        Icons.Rounded.EditRoad,
                        contentDescription = "",
                        modifier = Modifier.padding(end = 12.dp)
                    )

                    Text(
                        text = "Выбрать участок дороги",
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }
            }
        }

        Text(
            modifier = Modifier.padding(bottom = 16.dp, top = 12.dp),
            text = "Выбранная дата: ${uiState.selectedDate.formatDate()} \n " +
                "Выбранный участок дороги: ${uiState.selectedRoadSection?.name}",
            fontSize = 14.sp
        )

        RoadSectionDialog(uiState.roadSections, showRoadSectionDialog) {
            viewModel.updateSelectedRoadSection(it)
        }

        if (bookingOverviewDialog.value != null) {
            BookingOverviewDialog(bookingOverviewDialog, showOverviewDialog)
        }
        LazyVerticalGrid(
            columns = GridCells.FixedSize(64.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            items(uiState.bookings.asBookingSlots()) { booking ->

                BookingItem(booking) {
                    showOverviewDialog.value = true
                    bookingOverviewDialog.value = null
                }
            }
        }
    }
}
