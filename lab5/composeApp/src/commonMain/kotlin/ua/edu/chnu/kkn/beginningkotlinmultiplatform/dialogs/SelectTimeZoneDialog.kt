package ua.edu.chnu.kkn.beginningkotlinmultiplatform.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone

@Composable
fun SelectTimeZoneDialog(
    selectedIds: List<String>,
    onConfirm: (List<String>) -> Unit,
    onDismiss: () -> Unit
) {
    val allZones = remember { TimeZone.availableZoneIds.sorted() }
    var searchQuery by remember { mutableStateOf("") }
    val tempSelection = remember(selectedIds) { mutableStateListOf(*selectedIds.toTypedArray()) }

    val filteredZones = allZones.filter { zoneId ->
        searchQuery.isBlank() || zoneId.contains(searchQuery, ignoreCase = true)
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Timezones") },
        text = {
            Column {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    label = { Text("Search timezone") },
                    singleLine = true
                )
                LazyColumn(modifier = Modifier.heightIn(max = 360.dp)) {
                    items(filteredZones, key = { it }) { zoneId ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    if (zoneId in tempSelection) tempSelection.remove(zoneId)
                                    else tempSelection.add(zoneId)
                                }
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = zoneId in tempSelection,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) tempSelection.add(zoneId) else tempSelection.remove(zoneId)
                                }
                            )
                            Text(zoneId)
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(tempSelection.toList()) }) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
