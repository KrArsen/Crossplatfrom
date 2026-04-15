package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.radiobuttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
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
fun RadioButtonsScreen(onBackClick: () -> Unit) {
    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4")
    var selectedOption by remember { mutableStateOf(options.first()) }

    ScreenScaffold(title = "Radio Buttons", onBackClick = onBackClick) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            options.forEach { option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedOption == option,
                        onClick = { selectedOption = option }
                    )
                    Text(option)
                }
            }
        }
    }
}

@Preview
@Composable
private fun RadioButtonsScreenPreview() {
    AppTheme {
        RadioButtonsScreen(onBackClick = {})
    }
}
