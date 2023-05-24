package com.company.autocontrol.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.autocontrol.model.RoadSection

@Composable
fun RoadSectionDialog(
    sections: List<RoadSection>,
    showDialog: MutableState<Boolean>,
    onSelectedRoadSection: (RoadSection) -> Unit,
) {
    if (showDialog.value) {
        AlertDialog(
            modifier = Modifier.fillMaxHeight(0.85f),
            onDismissRequest = { showDialog.value = false },
            title = { Text("Выберите участок дороги") },
            text = {
                LazyColumn {
                    items(sections) { item ->
                        Text(
                            text = item.name,
                            fontSize = 18.sp,
                            modifier = Modifier.fillMaxWidth()
                                .clickable {
                                    onSelectedRoadSection(item)
                                    showDialog.value = false
                                }
                                .padding(8.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showDialog.value = false }) {
                    Text("Закрыть")
                }
            }
        )
    }
}
