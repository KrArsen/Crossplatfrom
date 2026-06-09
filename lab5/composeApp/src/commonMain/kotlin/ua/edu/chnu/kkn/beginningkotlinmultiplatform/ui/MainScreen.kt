package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.meeting.MeetingScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.timezones.TimeZonesScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.viewmodel.TimeZoneViewModel

@Composable
fun MainScreen(
    viewModel: TimeZoneViewModel,
    onNewWindowClick: (() -> Unit)? = null
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            if (onNewWindowClick != null) {
                Surface(
                    tonalElevation = 4.dp,
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("TimeZone Companion", style = MaterialTheme.typography.titleLarge)
                        Button(onClick = onNewWindowClick) {
                            Text("Open new window")
                        }
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.AccessTime, contentDescription = "Time Zones") },
                    label = { Text("Time Zones") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Groups, contentDescription = "Meeting") },
                    label = { Text("Meeting") }
                )
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> TimeZonesScreen(viewModel = viewModel, contentPadding = innerPadding)
            else -> MeetingScreen(viewModel = viewModel, contentPadding = innerPadding)
        }
    }
}
