package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared_mobile.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones.TimeZoneHelper
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones.TimeZoneHelperImpl
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared.components.NumberPicker
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared.dialogs.MeetingDialog
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared.dialogs.isSelected
import kotlin.collections.set

@Composable
fun FindMeetingPage(
    timezoneStrings: SnapshotStateList<String>
) {
    val listState = rememberLazyListState()
    val startTime = remember {
        mutableIntStateOf(8)
    }
    val endTime = remember {
        mutableIntStateOf(17)
    }
    val selectedTimeZones = remember {
        val selected = SnapshotStateMap<Int, Boolean>()
        for (i in timezoneStrings.indices) selected[i] = true
        selected
    }
    val timezoneHelper: TimeZoneHelper = TimeZoneHelperImpl()
    val showMeetingDialog = remember { mutableStateOf(false) }
    val meetingHours = remember { SnapshotStateList<Int>() }
    if (showMeetingDialog.value) {
        MeetingDialog(
            hours = meetingHours,
            onDismiss = {
                showMeetingDialog.value = false
            }
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = "Time Range",
            style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onBackground)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),

            ) {
            Spacer(modifier = Modifier.size(16.dp))
            NumberTimeCard("Start", startTime)
            Spacer(modifier = Modifier.size(32.dp))
            NumberTimeCard("End", endTime)
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)

        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                text = "Time Zones",
                style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onBackground)
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(
            modifier = Modifier
                .weight(0.6F)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            state = listState,
        ) {
            itemsIndexed(timezoneStrings) { i, timezone ->
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),

                    ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Checkbox(checked = isSelected(selectedTimeZones, i),
                            onCheckedChange = {
                                selectedTimeZones[i] = it
                            })
                        Text(timezone, modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
            }
        }
        Spacer(Modifier.weight(0.1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(start = 4.dp, end = 4.dp)

        ) {
            OutlinedButton(
                onClick = {
                    meetingHours.clear()
                    meetingHours.addAll(
                        timezoneHelper.search(
                            startTime.intValue,
                            endTime.intValue,
                            getSelectedTimeZones(timezoneStrings, selectedTimeZones)
                        )
                    )
                    showMeetingDialog.value = true
                }) {
                Text("Search")
            }
        }
        Spacer(Modifier.size(16.dp))
    }
}

@Composable
private fun NumberTimeCard(label: String, hour: MutableState<Int>) {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = label,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.size(16.dp))
            NumberPicker(hour = hour, range = IntRange(0, 23),
                onStateChanged = { hour.value = it }
            )
        }
    }
}

private fun getSelectedTimeZones(
    timezoneStrings: List<String>,
    selectedStates: Map<Int, Boolean>
): List<String> {
    val selectedTimezones = mutableListOf<String>()
    selectedStates.keys.forEach {
        val timezone = timezoneStrings[it]
        if (isSelected(selectedStates, it) && !selectedTimezones.contains(timezone)) {
            selectedTimezones.add(timezone)
        }
    }
    return selectedTimezones
}
