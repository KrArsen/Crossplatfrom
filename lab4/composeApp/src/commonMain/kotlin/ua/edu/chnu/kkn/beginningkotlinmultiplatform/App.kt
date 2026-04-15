package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.material3.MaterialTheme
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.MainScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.viewmodel.TimeZoneViewModel

@Composable
fun App() {
    MaterialTheme {
        val viewModel = remember { TimeZoneViewModel() }
        MainScreen(viewModel)
    }
}
