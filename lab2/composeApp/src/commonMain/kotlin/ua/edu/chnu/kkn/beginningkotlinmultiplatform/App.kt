package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones.DateTimeZoneService
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones.KotlinxDateTimeZoneService
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.platform.initLogger
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.platform.platformName

@Composable
fun App(
    dateTimeZoneService: DateTimeZoneService = KotlinxDateTimeZoneService()
) {
    val nowInstant = dateTimeZoneService.nowInstant()
    val currentZoneId = dateTimeZoneService.currentSystemTimeZoneId()
    val localDateTime = dateTimeZoneService.format(nowInstant, currentZoneId)
    val knownZoneCount = dateTimeZoneService.availableTimeZoneIds().size

    LaunchedEffect(Unit) {
        initLogger()
        Napier.i(
            message = "Platform=${platformName()}, zone=$currentZoneId, instant=$nowInstant, knownZones=$knownZoneCount",
            tag = "App"
        )
    }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Laboratory work #2")
            Text(text = "Platform: ${platformName()}")
            Text(text = "Current zone: $currentZoneId")
            Text(text = "Local date/time: $localDateTime")
            Text(text = "Available time zones: $knownZoneCount")
        }
    }
}
