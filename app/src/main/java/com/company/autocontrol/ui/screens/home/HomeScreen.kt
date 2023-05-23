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
import com.company.autocontrol.model.RoadSection
import com.company.autocontrol.ui.BookingItem
import com.company.autocontrol.ui.viewmodel.BookingViewModel
import com.company.autocontrol.util.formatDate
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.LocalDate
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: BookingViewModel = hiltViewModel()) {
    val uiState by viewModel.bookingUiState.collectAsStateWithLifecycle()
    val calendarState = rememberUseCaseState()
    val showDialog = remember { mutableStateOf(false) }
    val selectedRoadSection = remember { mutableStateOf(uiState.roadSections.first()) } // TODO краш, если список будет пуст
    var date by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            locale = Locale("ru"),
            style = CalendarStyle.MONTH,
            yearSelection = true,
            monthSelection = true
        ),
        selection = CalendarSelection.Date {
            date = it
        }
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Row {
            CompositionLocalProvider(
                LocalMinimumInteractiveComponentEnforcement provides false
            ) {
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

            CompositionLocalProvider(
                LocalMinimumInteractiveComponentEnforcement provides false
            ) {
                Button(
                    onClick = {
                        showDialog.value = true
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
            text = "Выбранная дата: ${date?.formatDate()} \n " +
                    "Выбранный участок дороги: ${selectedRoadSection.value.name}",
            fontSize = 14.sp
        )

        RoadSectionDialog(uiState.roadSections, showDialog, selectedRoadSection)

        LazyVerticalGrid(
            columns = GridCells.FixedSize(64.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            items(uiState.bookings) { booking ->
                BookingItem(booking)
            }
        }
    }
}
