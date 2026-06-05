package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.timezones

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.update
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.dialogs.SelectTimeZoneDialog
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.showDialog
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.LocalDialogController
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.viewmodel.TimeZoneViewModel

@Composable
fun TimeZonesScreen(
    viewModel: TimeZoneViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val selectedZones = viewModel.selectedTimeZones.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { UserTimeZoneCard() }
            items(selectedZones, key = { it }) { zoneId ->
                TimeZoneCard(
                    zoneId = zoneId,
                    onRemove = { viewModel.removeTimeZone(zoneId) }
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                showDialog(
                    title = "Select Timezones",
                    onDismiss = {},
                    content = {
                        val controller = LocalDialogController.current
                        SelectTimeZoneDialog(
                            selectedIds = selectedZones,
                            onConfirm = { updatedSelection ->
                                viewModel.selectedTimeZones.update { updatedSelection.distinct() }
                                controller.close()
                            },
                            onDismiss = { controller.close() }
                        )
                    }
                )
            }
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add timezone")
        }
    }
}
