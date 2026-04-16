package ua.edu.chnu.kkn.beginningkotlinmultiplatform.ui.shared.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import beginningkotlinmultiplatform.composeapp.generated.resources.Res
import beginningkotlinmultiplatform.composeapp.generated.resources.add
import beginningkotlinmultiplatform.composeapp.generated.resources.cancel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones.TimeZoneHelper
import ua.edu.chnu.kkn.beginningkotlinmultiplatform.data.timezones.TimeZoneHelperImpl

@Composable
expect fun AddTimeDialogWrapper(onDismiss: () -> Unit, content: @Composable () -> Unit)

@Composable
internal fun AddTimeZoneDialog(
    onAdd: (List<String>) -> Unit,
    onDismiss: () -> Unit
) {

    val timezoneHelper: TimeZoneHelper = TimeZoneHelperImpl()

    AddTimeDialogWrapper(onDismiss) {

        Surface(
            border = BorderStroke(width = 1.dp, color = Color.Black),
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val timeZoneStrings by remember {
                    mutableStateOf(
                        timezoneHelper.getTimeZoneStrings().toList()
                    )
                }
                val selectedStates = remember { SnapshotStateMap<Int, Boolean>() }
                val listState = rememberLazyListState()
                val searchValue = remember { mutableStateOf("") }
                val coroutineScope = rememberCoroutineScope()
                val focusRequester = remember { FocusRequester() }

                OutlinedTextField(
                    singleLine = true,
                    value = searchValue.value,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
                    onValueChange = {
                        searchValue.value = it
                        if (searchValue.value.isEmpty()) {
                            return@OutlinedTextField
                        }
                        val index =
                            searchZones(searchValue.value, timeZoneStrings = timeZoneStrings)
                        if (index != -1) {
                            coroutineScope.launch {
                                listState.animateScrollToItem(index)
                            }
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            searchValue.value = ""
                        }) {
                            Icon(
                                Icons.Filled.Clear,
                                tint = MaterialTheme.colorScheme.secondary,
                                contentDescription = "Cancel",
                            )
                        }
                    }
                )
                DisposableEffect(Unit) {
                    focusRequester.requestFocus()
                    onDispose { }
                }
                Spacer(modifier = Modifier.size(16.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentPadding = PaddingValues(16.dp),
                    state = listState,

                    ) {
                    itemsIndexed(timeZoneStrings) { i, timezone ->
                        Surface(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            color = if (isSelected(selectedStates, i))
                                MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background

                        ) {
                            Row(
                                modifier = Modifier
                                    .toggleable(
                                        value = isSelected(selectedStates, i),
                                        onValueChange = {
                                            selectedStates[i] = it
                                        })
                                    .fillMaxWidth(),
                            ) {
                                Text(timezone)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    modifier = Modifier.align(Alignment.End),
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text(text = stringResource(Res.string.cancel))
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                    Button(
                        onClick = {
                            onAdd(
                                getTimezones(
                                    selectedStates = selectedStates,
                                    timeZoneStrings = timeZoneStrings
                                )
                            )
                        }
                    ) {
                        Text(text = stringResource(Res.string.add))
                    }
                }
            }
        }
    }
}

internal fun searchZones(searchString: String, timeZoneStrings: List<String>): Int {
    var timezone = timeZoneStrings.firstOrNull { it.startsWith(searchString, ignoreCase = true) }
    if (timezone == null) {
        timezone = timeZoneStrings.firstOrNull { it.contains(searchString, ignoreCase = true) }
    }
    if (timezone != null) {
        return timeZoneStrings.indexOf(timezone)
    }
    return -1
}

internal fun getTimezones(selectedStates: Map<Int, Boolean>, timeZoneStrings: List<String>): List<String> {
    val timezoneIndexes = selectedStates.map { if (it.value) it.key else -1 }
    val timezones = mutableListOf<String>()
    timezoneIndexes.forEach { index ->
        if (index != -1) {
            timezones.add(timeZoneStrings[index])
        }
    }
    return timezones
}

internal fun isSelected(selectedStates: Map<Int, Boolean>, index: Int): Boolean {
    return (selectedStates.containsKey(index) && (true == selectedStates[index]))
}
