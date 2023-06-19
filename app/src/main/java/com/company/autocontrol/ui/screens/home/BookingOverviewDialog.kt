package com.company.autocontrol.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.company.autocontrol.R
import com.company.autocontrol.data.model.booking.Booking
import com.company.autocontrol.data.model.booking.BookingType

@Composable
fun BookingOverviewDialog(
    booking: MutableState<Booking?>,
    showDialog: MutableState<Boolean>
) {
    val booking by booking

    if (showDialog.value && booking != null) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Бронь") },
            text = {
                Column {
                    Text("Инициатор брони: ${booking!!.author}")
                    Text("Отдел: ${booking!!.department}")
                    Text("Время брони: ${booking!!.time}")
                    Text("Тип брони: ${booking!!.bookingType}")
                    Text("Статус брони: ${booking!!.bookingStatus}")
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
        mutableStateOf<Booking?>(
            Booking(
                bookingType = BookingType.NO_BOOKING,
                time = "13:30",
                comment = "comment",
                author = "author",
                department = "department"
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
