package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.meeting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TimePickerRow(label: String, hour: Int, onHourChange: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, modifier = Modifier.weight(1f))
        IconButton(onClick = { onHourChange((hour - 1 + 24) % 24) }) {
            Text("−")
        }
        Text("${hour.toString().padStart(2, '0')}:00", style = MaterialTheme.typography.titleLarge)
        IconButton(onClick = { onHourChange((hour + 1) % 24) }) {
            Text("+")
        }
    }
}
