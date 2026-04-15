package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.divider

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.components.ScreenScaffold
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

@Composable
fun DividerScreen(onBackClick: () -> Unit) {
    ScreenScaffold(title = "Divider", onBackClick = onBackClick) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("HorizontalDivider example")
            HorizontalDivider(modifier = Modifier.fillMaxWidth())

            Text("VerticalDivider example")
            Row(
                modifier = Modifier.height(60.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Left")
                VerticalDivider(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(50.dp)
                )
                Text("Right")
            }
        }
    }
}

@Preview
@Composable
private fun DividerScreenPreview() {
    AppTheme {
        DividerScreen(onBackClick = {})
    }
}
