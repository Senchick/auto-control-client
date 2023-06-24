package com.company.autocontrol.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.autocontrol.R
import com.company.autocontrol.data.model.booking.Booking
import com.company.autocontrol.data.model.booking.BookingDialog
import com.company.autocontrol.data.model.booking.BookingSlot
import com.company.autocontrol.data.model.booking.BookingType
import com.company.autocontrol.data.model.user.Role
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingOverviewDialog(
    booking: MutableState<BookingSlot>,
    showDialog: MutableState<Boolean>,
    role: Role,
    onSave: (Booking) -> Unit
) {
    val booking by booking

    if (showDialog.value) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")
        val clockState = rememberUseCaseState()
        val selectedTime = remember { mutableStateOf(booking.time) }
        val bookingDialog by remember { mutableStateOf(BookingDialog()) }

        ClockDialog(
            state = clockState,
            selection = ClockSelection.HoursMinutes { hours, minutes ->
                selectedTime.value = LocalTime.of(hours, minutes, 0)
            },
            config = ClockConfig(
                boundary = LocalTime.of(6, 0, 0)..LocalTime.of(23, 0, 0),
                defaultTime = selectedTime.value,
                is24HourFormat = true
            ),
        )

        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Бронь") },
            text = {
                Column {
                    OutlinedDropDownMenu("Тип брони", listOf("Общая", "Моно", "Закрыта", "Не выбрано"), 3) { i ->
                        bookingDialog.bookingType = BookingType.values().find { it.ordinal == i }!!
                    }

                   /* Text("Статус брони: ${booking!!.bookingStatus}")
                    Text("Инициатор брони: ${booking!!.author.firstname} ${booking!!.author.surname}")
                    Text("Отдел: ${booking!!.author.department}")
                    Text("Начало брони: ${booking!!.from.format(formatter)}")
                    Text("Конец брони: ${booking!!.to.format(formatter)}")
                    Text("Дата создания брони: ${booking!!.createdDate.format(formatter)}")
                    Text("Комментарий: ${booking!!.comment}")*/
                }
            },
            confirmButton = {
                Button(onClick = {
                    showDialog.value = false
                    onSave(booking.booking!!)
                }) {
                    Text(text = stringResource(R.string.title_save))
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedDropDownMenu(
    title: String,
    items: List<String>,
    defaultValue: Int,
    onItemSelected: (Int) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(items[defaultValue]) }

    val shape = if (expanded) {
        RoundedCornerShape(8.dp).copy(
            bottomEnd = CornerSize(0.dp),
            bottomStart = CornerSize(0.dp)
        )
    } else {
        RoundedCornerShape(8.dp)
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            textStyle = TextStyle.Default.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            ),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(title, fontWeight = FontWeight.Bold,) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            shape = shape,
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onItemSelected(index)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
