package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.aakira.napier.Napier
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones.DateTimeProvider
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones.DateTimeProviderImpl
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.platform.initLogger
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.platform.platformName

@Composable
fun App(
    dateTimeProvider: DateTimeProvider = DateTimeProviderImpl()
) {
    val currentDateTime = remember { dateTimeProvider.getCurrentLocalDateTime() }
    val currentTimeZone = remember { dateTimeProvider.getCurrentTimeZone() }

    LaunchedEffect(Unit) {
        initLogger()
        Napier.i(
            message = "App started. Current platform: ${platformName()}. Current Date/Time: $currentDateTime, TimeZone: $currentTimeZone",
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
            Text(text = "Current zone: $currentTimeZone")
            Text(text = "Local date/time: $currentDateTime")
        }
    }
}
