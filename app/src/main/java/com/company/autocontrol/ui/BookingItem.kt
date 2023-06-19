package com.company.autocontrol.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.autocontrol.data.model.booking.Booking
import com.company.autocontrol.data.model.booking.BookingType
import com.company.autocontrol.ui.theme.green
import com.company.autocontrol.ui.theme.red
import com.company.autocontrol.ui.theme.blue

@Composable
fun BookingItem(
    booking: Booking,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable(booking.bookingType != BookingType.CLOSE) { onClick() }
    ) {
        Box(
            modifier = Modifier.background(
                color = when (booking.bookingType) {
                    BookingType.GENERAL -> MaterialTheme.colorScheme.green
                    BookingType.CLOSE -> MaterialTheme.colorScheme.red
                    BookingType.MONO -> MaterialTheme.colorScheme.blue
                    BookingType.NO_BOOKING -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                }
            ).fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = booking.time,
                fontSize = 14.sp,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }
}
