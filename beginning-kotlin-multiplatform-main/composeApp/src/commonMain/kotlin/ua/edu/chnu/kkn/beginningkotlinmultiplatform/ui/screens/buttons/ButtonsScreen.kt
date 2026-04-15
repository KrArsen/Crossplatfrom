package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.buttons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

@Composable
fun ButtonsScreen(
    onFilledButtonClicked: (text: String) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                onFilledButtonClicked.invoke(("Filled"))
            }
        ) {
            Text("Filled")
        }

        FilledTonalButton(
            onClick = {

            }
        ) {
            Text("Filled tonal")
        }

        OutlinedButton(onClick = {

        }) {
            Text("Outlined")
        }

        ElevatedButton(onClick = {

        }) {
            Text("Elevated")
        }

        TextButton(
            onClick = { }
        ) {
            Text("Text Button")
        }

        Button(
            onClick = {

            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Filled with icon"
                )
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
        FloatingActionButton(
            onClick = { },
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    }
}

@Preview
@Composable
fun ButtonsScreenPreview() {
    AppTheme {
        Scaffold {
            ButtonsScreen({ text -> })
        }
    }
}
