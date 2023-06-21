package com.company.autocontrol.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.company.autocontrol.R
import com.company.autocontrol.data.model.booking.BookingOverview
import com.company.autocontrol.data.model.booking.BookingStatus
import com.company.autocontrol.data.model.booking.BookingType
import com.company.autocontrol.data.model.user.Role
import com.company.autocontrol.data.model.user.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun BookingOverviewDialog(
    booking: MutableState<BookingOverview?>,
    showDialog: MutableState<Boolean>
) {
    val booking by booking

    if (showDialog.value && booking != null) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy")

        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Бронь") },
            text = {
                Column {
                    Text("Тип брони: ${booking!!.bookingType}")
                    Text("Статус брони: ${booking!!.bookingStatus}")
                    Text("Инициатор брони: ${booking!!.author.firstname} ${booking!!.author.surname}")
                    Text("Отдел: ${booking!!.author.department}")
                    Text("Начало брони: ${booking!!.fromInterval.format(formatter)}")
                    Text("Конец брони: ${booking!!.toInterval.format(formatter)}")
                    Text("Дата создания брони: ${booking!!.createdDate.format(formatter)}")
                    Text("Комментарий: ${booking!!.comment}")
                }
            },
            confirmButton = {
                Button(onClick = {
                    showDialog.value = false
                }) {
                    Text(text = stringResource(R.string.title_save))
                }
            }
        )
    }
}

@Preview(widthDp = 500, heightDp = 500)
@Composable
private fun PreviewDialog() {
    val booking = remember {
        mutableStateOf<BookingOverview?>(
            BookingOverview(
                bookingType = BookingType.NO_BOOKING,
                createdDate = LocalDateTime.now(),
                comment = "comment",
                author = User(
                    firstname = "Ivan",
                    surname = "Ivanov",
                    department = "Department",
                    login = "",
                    role = Role.USER
                ),
                fromInterval = LocalDateTime.now(),
                toInterval = LocalDateTime.now(),
                bookingStatus = BookingStatus.IDLE

            )
        )
    }

    val showDialog = remember {
        mutableStateOf(true)
    }

    BookingOverviewDialog(
        booking,
        showDialog
    )
}
