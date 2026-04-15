package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.screens.AppNavigation
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.theme.AppTheme

@Composable
@Preview
fun App() {
    AppTheme {
        AppNavigation()
    }
}
