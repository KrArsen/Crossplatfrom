package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.meeting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.dialogs.MeetingResultDialog
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.viewmodel.MeetingSlot
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.viewmodel.TimeZoneViewModel

@Composable
fun MeetingScreen(
    viewModel: TimeZoneViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var startHour by remember { mutableIntStateOf(9) }
    var endHour by remember { mutableIntStateOf(18) }
    var showResult by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<List<MeetingSlot>?>(null) }
    val selectedZones = viewModel.selectedTimeZones.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp)
    ) {
        Text("Find meeting time", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        TimePickerRow(
            label = "Work starts at",
            hour = startHour,
            onHourChange = { startHour = it }
        )
        TimePickerRow(
            label = "Work ends at",
            hour = endHour,
            onHourChange = { endHour = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                result = viewModel.findMeetingTime(startHour = startHour, endHour = endHour)
                showResult = true
            }
        ) {
            Text("Find suitable time")
        }
    }

    if (showResult && result != null) {
        MeetingResultDialog(
            slots = result.orEmpty(),
            selectedZones = selectedZones,
            onDismiss = { showResult = false }
        )
    }
}
