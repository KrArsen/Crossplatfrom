package ua.edu.chnu.kkn.beginningkotlinmultiplatform

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.MaterialTheme
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.MainScreen
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.viewmodel.TimeZoneViewModel

@Composable
fun App(
    onNewWindowClick: (() -> Unit)? = null,
    onOpenDialogClick: (() -> Unit)? = null
) {
    MaterialTheme {
        val viewModel = remember { TimeZoneViewModel() }
        
        Box(modifier = Modifier.fillMaxSize()) {
            MainScreen(
                viewModel = viewModel,
                onNewWindowClick = onNewWindowClick
            )
            
            activeWebDialogs.toList().forEach { dialogData ->
                Dialog(
                    onDismissRequest = {
                        dialogData.onDismiss()
                        activeWebDialogs.remove(dialogData)
                    }
                ) {
                    dialogData.content()
                }
            }
        }
    }
}
