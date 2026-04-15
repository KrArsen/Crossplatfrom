package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.checkboxes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.components.ScreenScaffold
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

@Composable
fun CheckboxesScreen(onBackClick: () -> Unit) {
    ScreenScaffold(title = "Checkboxes", onBackClick = onBackClick) { padding ->
        CheckboxesContent(padding = padding)
    }
}

@Composable
private fun CheckboxesContent(padding: PaddingValues) {
    var checked by remember { mutableStateOf(true) }
    var unchecked by remember { mutableStateOf(false) }
    var triState by remember { mutableStateOf(ToggleableState.Indeterminate) }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(16.dp)
    ) {
        Checkbox(checked = checked, onCheckedChange = { checked = it })
        Checkbox(checked = unchecked, onCheckedChange = { unchecked = it })
        TriStateCheckbox(
            state = triState,
            onClick = {
                triState = when (triState) {
                    ToggleableState.Off -> ToggleableState.On
                    ToggleableState.On -> ToggleableState.Indeterminate
                    ToggleableState.Indeterminate -> ToggleableState.Off
                }
            }
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked, onCheckedChange = { checked = it })
            Text("Checkbox with label")
        }
    }
}

@Preview
@Composable
fun CheckboxesScreenPreview() {
    AppTheme {
        CheckboxesContent(PaddingValues(0.dp))
    }
}
