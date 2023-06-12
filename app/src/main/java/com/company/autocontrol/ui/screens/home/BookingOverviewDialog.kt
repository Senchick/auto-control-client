package com.company.autocontrol.ui.screens.home

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.company.autocontrol.R
import com.company.autocontrol.data.model.booking.Booking

@Composable
fun BookingOverviewDialog(
    booking: MutableState<Booking?>,
    showDialog: MutableState<Boolean>
) {
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Бронь") },
            text = {
                Row {
                    Text("Инициатор брони")
                    Text("Время брони")
                    Text("Тип брони")
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
