package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.timezones

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TimeZoneCard(zoneId: String, onRemove: (() -> Unit)? = null) {
    var now by remember { mutableStateOf(Clock.System.now()) }

    LaunchedEffect(zoneId) {
        while (true) {
            now = Clock.System.now()
            delay(1000)
        }
    }

    TimeZoneCardContent(
        title = zoneId,
        zoneId = zoneId,
        now = now,
        onRemove = onRemove
    )
}

@Composable
fun UserTimeZoneCard() {
    val userZone = TimeZone.currentSystemDefault().id
    var now by remember { mutableStateOf(Clock.System.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            now = Clock.System.now()
            delay(1000)
        }
    }

    TimeZoneCardContent(
        title = "Your timezone",
        zoneId = userZone,
        now = now,
        onRemove = null
    )
}

@Composable
private fun TimeZoneCardContent(
    title: String,
    zoneId: String,
    now: kotlinx.datetime.Instant,
    onRemove: (() -> Unit)?
) {
    val zone = TimeZone.of(zoneId)
    val dateTime = now.toLocalDateTime(zone)
    val offset = zone.offsetAt(now)
    val totalSeconds = offset.totalSeconds
    val sign = if (totalSeconds >= 0) "+" else "-"
    val absSeconds = kotlin.math.abs(totalSeconds)
    val offsetHours = absSeconds / 3600
    val offsetMinutes = (absSeconds % 3600) / 60
    val formattedOffset = "UTC$sign$offsetHours:${offsetMinutes.toString().padStart(2, '0')}"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(
                    "${dateTime.hour.twoDigits()}:${dateTime.minute.twoDigits()}:${dateTime.second.twoDigits()}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(formattedOffset, style = MaterialTheme.typography.bodySmall)
            }
            if (onRemove != null) {
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Close, contentDescription = "Remove timezone")
                }
            }
        }
    }
}

private fun Int.twoDigits(): String = toString().padStart(2, '0')
