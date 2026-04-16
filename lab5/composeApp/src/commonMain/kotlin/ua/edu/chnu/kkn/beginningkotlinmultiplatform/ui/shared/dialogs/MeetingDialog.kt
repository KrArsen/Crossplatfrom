package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import beginningkotlinmultiplatform.composeapp.generated.resources.Res
import beginningkotlinmultiplatform.composeapp.generated.resources.done
import org.jetbrains.compose.resources.stringResource

@Composable
expect fun MeetingDialogWrapper(onDismiss: () -> Unit, content: @Composable () -> Unit)

@Composable
fun MeetingDialog(
    hours: SnapshotStateList<Int>,
    onDismiss: () -> Unit
) {
    MeetingDialogWrapper(onDismiss) {
        Surface(
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val listState = rememberLazyListState()
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = "Meeting Times",
                    style = MaterialTheme.typography.bodyLarge
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentPadding = PaddingValues(16.dp),
                    state = listState,

                    ) {
                    items(hours) { hour ->
                        Surface(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                            ) {
                                Text(hour.toString())
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        onDismiss()
                    }
                ) {
                    Text(text = stringResource(Res.string.done))
                }

            }
        }
    }
}
