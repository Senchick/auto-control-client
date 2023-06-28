package com.company.autocontrol.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.autocontrol.R
import com.company.autocontrol.data.model.booking.*
import com.company.autocontrol.data.model.user.Role
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingOverviewDialog(
    booking: MutableState<BookingSlot>,
    showDialog: MutableState<Boolean>,
    role: Role,
    onSave: (BookingDialog) -> Unit
) {
    val booking by booking

    if (showDialog.value) {
        val formatterDate = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val clockStateFrom = rememberUseCaseState()
        val clockStateTo = rememberUseCaseState()

        var bookingDialog by remember {
            mutableStateOf(
                BookingDialog(
                    date = booking.date,
                    from = booking.booking?.from ?: booking.date,
                    to = booking.booking?.to ?: booking.date,
                    roadSectionId = booking.roadSection.id,
                    bookingType = booking.bookingType,
                    comment = booking.booking?.comment ?: "",
                    bookingStatus = booking.booking?.bookingStatus ?: BookingStatus.WAITING
                )
            )
        }
        var selectedFromTime by remember { mutableStateOf(booking.booking?.from?.toLocalTime() ?: booking.time) }
        var selectedToTime by remember { mutableStateOf(booking.booking?.to?.toLocalTime() ?: booking.time) }

        ClockDialog(
            state = clockStateFrom,
            selection = ClockSelection.HoursMinutes { hours, minutes ->
                selectedFromTime = LocalTime.of(hours, minutes, 0)
                bookingDialog = bookingDialog.copy(
                    from = selectedFromTime.atDate(LocalDate.now())
                )
            },
            config = ClockConfig(
                boundary = LocalTime.of(6, 0, 0)..(bookingDialog.to?.toLocalTime() ?: LocalTime.of(23, 0, 0)),
                defaultTime = selectedFromTime,
                is24HourFormat = true
            ),
        )
        ClockDialog(
            state = clockStateTo,
            selection = ClockSelection.HoursMinutes { hours, minutes ->
                selectedToTime = LocalTime.of(hours, minutes, 0)
                bookingDialog = bookingDialog.copy(
                    to = selectedToTime.atDate(LocalDate.now())
                )
            },
            config = ClockConfig(
                boundary = (bookingDialog.from?.toLocalTime() ?: LocalTime.of(6, 0, 0))..LocalTime.of(23, 0, 0),
                defaultTime = selectedToTime,
                is24HourFormat = true
            ),
        )

        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Бронь") },
            text = {
                Column(verticalArrangement = Arrangement.SpaceBetween) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .weight(1f, false)
                    ) {
                        val authorBooking = booking.author?.let { "${it.firstname} ${it.surname}" } ?: "Вы"
                        Text("Инициатор брони: $authorBooking")

                        Text("Отдел: " + (booking.author?.department ?: "Ваш отдел"))

                        val createdDate =
                            booking.booking?.createdDate?.format(formatterDate) ?: LocalDateTime.now().format(
                                formatterDate
                            )
                        Text("Дата создания брони: $createdDate")

                        OutlinedDropDownMenu(
                            title = "Тип брони",
                            items = listOf("Общая", "Моно", "Закрыта", "Не выбрано"),
                            modifier = Modifier.padding(top = 8.dp),
                            defaultValue = booking.booking?.bookingType?.ordinal ?: 3,
                            errorMessage = if (bookingDialog.bookingType == BookingType.CLOSE && role == Role.USER) {
                                "Вы не можете выбрать закрытую дорогу"
                            } else {
                                null
                            }
                        ) { i ->
                            bookingDialog =
                                bookingDialog.copy(bookingType = BookingType.values().find { it.ordinal == i })
                        }
                        if (role != Role.USER) {
                            OutlinedDropDownMenu(
                                title = "Статус брони",
                                items = listOf("Ожидание", "Бронь разрешена", "Бронь запрещена"),
                                modifier = Modifier.padding(top = 8.dp),
                                defaultValue = booking.booking?.bookingStatus?.ordinal ?: 0,
                            ) { i ->
                                bookingDialog = bookingDialog.copy(
                                    bookingStatus = BookingStatus.values().find { it.ordinal == i }
                                )
                            }
                        }

                        ReadonlyTextField(
                            value = TextFieldValue(selectedFromTime.format(formatterTime)),
                            onValueChange = {},
                            modifier = Modifier.padding(top = 8.dp),
                            onClick = { clockStateFrom.show() }
                        ) {
                            Text("Начало брони", fontWeight = FontWeight.Bold)
                        }

                        ReadonlyTextField(
                            value = TextFieldValue(selectedToTime.format(formatterTime)),
                            onValueChange = {},
                            modifier = Modifier.padding(top = 8.dp),
                            onClick = { clockStateTo.show() }
                        ) {
                            Text("Конец брони", fontWeight = FontWeight.Bold)
                        }

                        TextField(
                            textStyle = TextStyle.Default.copy(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light
                            ),
                            modifier = Modifier.padding(top = 8.dp),
                            placeholder = { Text("Введите комментарий") },
                            readOnly = booking.booking?.bookingStatus == BookingStatus.APPROVED ||
                                booking.booking?.bookingStatus == BookingStatus.DISAPPROVED,
                            value = bookingDialog.comment ?: "",
                            onValueChange = { bookingDialog = bookingDialog.copy(comment = it) },
                            label = { Text("Комментарий", fontWeight = FontWeight.Bold,) },
                            shape = RoundedCornerShape(8.dp),
                            colors = ExposedDropdownMenuDefaults.textFieldColors(
                                focusedIndicatorColor = Transparent,
                                unfocusedIndicatorColor = Transparent
                            ),
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        onSave(bookingDialog)
                    },
                    enabled = bookingDialog.let {
                        it.bookingStatus != null &&
                            it.roadSectionId != null &&
                            it.from != null &&
                            it.to != null &&
                            it.bookingType != null &&
                            it.comment != null && it.bookingStatus != BookingStatus.IDLE &&
                            it.bookingType != BookingType.NO_BOOKING &&
                            it.from != it.to
                    }
                ) {
                    Text(text = stringResource(R.string.title_save))
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false },

                ) {
                    Text(text = "Отмена")
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
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    enabled: Boolean = true,
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
        modifier = modifier,
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
            ),
            isError = errorMessage != null,
            supportingText = if (errorMessage != null) { { Text(text = errorMessage) } } else { null },
            enabled = enabled

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadonlyTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: @Composable () -> Unit
) {
    Box {
        TextField(
            textStyle = TextStyle.Default.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            ),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent
            ),
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            label = label,
            shape = RoundedCornerShape(8.dp)
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable(onClick = onClick),
        )
    }
}
