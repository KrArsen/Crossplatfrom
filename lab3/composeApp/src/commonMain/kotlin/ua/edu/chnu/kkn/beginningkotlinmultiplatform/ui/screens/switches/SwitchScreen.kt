package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.switches

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.components.ScreenScaffold
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

@Composable
fun SwitchScreen(onBackClick: () -> Unit) {
    var wifiEnabled by remember { mutableStateOf(true) }
    var bluetoothEnabled by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    ScreenScaffold(title = "Switch", onBackClick = onBackClick) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SwitchRow("Wi-Fi", wifiEnabled) { wifiEnabled = it }
            SwitchRow("Bluetooth", bluetoothEnabled) { bluetoothEnabled = it }
            SwitchRow("Notifications", notificationsEnabled) { notificationsEnabled = it }
        }
    }
}

@Composable
private fun SwitchRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview
@Composable
private fun SwitchScreenPreview() {
    AppTheme {
        SwitchScreen(onBackClick = {})
    }
}
