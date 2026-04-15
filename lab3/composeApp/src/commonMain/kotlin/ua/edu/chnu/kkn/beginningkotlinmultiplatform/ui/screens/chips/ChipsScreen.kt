package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.chips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.InputChip
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.components.ScreenScaffold
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

@Composable
fun ChipsScreen(onBackClick: () -> Unit) {
    ScreenScaffold(title = "Chips", onBackClick = onBackClick) { padding ->
        ChipsContent(padding)
    }
}

@Composable
private fun ChipsContent(padding: PaddingValues) {
    var filterSelected by remember { mutableStateOf(false) }
    var inputVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AssistChip(
            onClick = {},
            label = { Text("Assist Chip") }
        )
        FilterChip(
            selected = filterSelected,
            onClick = { filterSelected = !filterSelected },
            label = { Text("Filter Chip") }
        )
        if (inputVisible) {
            InputChip(
                selected = true,
                onClick = { inputVisible = false },
                label = { Text("Input Chip (tap to remove)") }
            )
        }
        SuggestionChip(
            onClick = {},
            label = { Text("Suggestion Chip") }
        )
    }
}

@Preview
@Composable
private fun ChipsScreenPreview() {
    AppTheme {
        ChipsContent(PaddingValues(0.dp))
    }
}
