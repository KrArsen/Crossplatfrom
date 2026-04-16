package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import beginningkotlinmultiplatform.composeapp.generated.resources.Res
import beginningkotlinmultiplatform.composeapp.generated.resources.add_timezones
import org.jetbrains.compose.resources.stringResource

@Composable
actual fun AddTimeDialogWrapper(
    onDismiss: () -> Unit,
    content: @Composable (() -> Unit)
) {

    DialogWindow(onCloseRequest = { onDismiss() },
        state = rememberDialogState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(width = 400.dp, height = Dp.Unspecified),
        ),
        title = stringResource(Res.string.add_timezones),
        content = {
            content()
        }
    )
}