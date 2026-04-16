package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared_mobile.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones.TimeZoneHelper
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones.TimeZoneHelperImpl
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared.components.AnimatedSwipeDismiss

const val timeMillis = 1000 * 60L

@Composable
internal fun TimeZonesPage(
    currentTimezoneStrings: SnapshotStateList<String>,
) {

    val timezoneHelper: TimeZoneHelper = TimeZoneHelperImpl()
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        var time by remember { mutableStateOf(timezoneHelper.currentTime()) }
        LaunchedEffect(Unit) {
            while (true) {
                time = timezoneHelper.currentTime()
                delay(timeMillis)
            }
        }
        LocalTimeCard(
            city = timezoneHelper.currentTimeZone(),
            time = time, date = timezoneHelper.getDate(timezoneHelper.currentTimeZone())
        )
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(
            state = listState,
        ) {
            itemsIndexed(currentTimezoneStrings, key = { _, timezone -> timezone }) { _, timezoneString ->
                AnimatedSwipeDismiss(
                    item = timezoneString,
                    background = { _ ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .height(50.dp)
                                .padding(
                                    start = 20.dp,
                                    end = 20.dp
                                )
                        ) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                            )
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier
                                    .align(Alignment.CenterEnd),
                            )
                        }
                    },
                    content = {
                        TimeCard(
                            timezone = timezoneString,
                            hours = timezoneHelper.hoursFromTimeZone(timezoneString),
                            time = timezoneHelper.getTime(timezoneString),
                            date = timezoneHelper.getDate(timezoneString)
                        )
                    },
                    onDismiss = { zone ->
                        if (currentTimezoneStrings.contains(zone)) {
                            currentTimezoneStrings.remove(zone)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun LocalTimeCard(city: String, time: String, date: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(all = 8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth()
        )
        {
            Box(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Spacer(modifier = Modifier.weight(1.0f))
                        Text("Your Location", style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(8.dp))
                        Text(city, style = MaterialTheme.typography.headlineSmall)
                        Spacer(Modifier.height(8.dp))
                    }
                    Spacer(modifier = Modifier.weight(1.0f))
                    Column(horizontalAlignment = Alignment.End) {
                        Spacer(modifier = Modifier.weight(1.0f))
                        Text(time, style = MaterialTheme.typography.headlineSmall)
                        Spacer(Modifier.height(8.dp))
                        Text(date, style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TimeCard(timezone: String, hours: Double, time: String, date: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(120.dp)
            .padding(8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Box(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start

                    ) {
                        Text(
                            timezone, style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        Spacer(modifier = Modifier.weight(1.0f))
                        Row {
                            Text(
                                hours.toString(), style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            )
                            Text(
                                " hours from local", style = TextStyle(
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1.0f))
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            time, style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        )
                        Spacer(modifier = Modifier.weight(1.0f))
                        Text(
                            date, style = TextStyle(
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }
    }
}


