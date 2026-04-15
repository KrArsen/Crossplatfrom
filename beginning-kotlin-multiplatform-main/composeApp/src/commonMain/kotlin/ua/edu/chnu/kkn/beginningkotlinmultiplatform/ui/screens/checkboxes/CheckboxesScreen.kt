package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.checkboxes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
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
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

@Composable
fun CheckboxesScreen() {

    var checked by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Minimal checkbox"
            )
            Checkbox(
                checked = checked,
                onCheckedChange = {
                    checked = it
                }
            )
        }
    }
}

@Preview
@Composable
fun CheckboxesScreenPreview() {
    AppTheme {
        Scaffold {
            CheckboxesScreen()
        }
    }
}
