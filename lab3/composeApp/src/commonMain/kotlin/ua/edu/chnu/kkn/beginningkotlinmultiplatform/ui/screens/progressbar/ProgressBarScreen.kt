package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.progressbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.components.ScreenScaffold
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

@Composable
fun ProgressBarScreen(onBackClick: () -> Unit) {
    var progress by remember { mutableFloatStateOf(0.4f) }

    ScreenScaffold(title = "Progress Bar", onBackClick = onBackClick) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("LinearProgressIndicator (determinate)")
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth()
            )
            Slider(
                value = progress,
                onValueChange = { progress = it }
            )
            Text("Progress: ${(progress * 100).toInt()}%")

            Text("CircularProgressIndicator (indeterminate)")
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun ProgressBarScreenPreview() {
    AppTheme {
        ProgressBarScreen(onBackClick = {})
    }
}
