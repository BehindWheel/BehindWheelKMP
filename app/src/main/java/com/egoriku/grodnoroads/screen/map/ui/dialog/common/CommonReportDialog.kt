package com.egoriku.grodnoroads.screen.map.ui.dialog.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.button.AlertButton
import com.egoriku.grodnoroads.foundation.dialog.common.DialogContent
import com.egoriku.grodnoroads.foundation.dialog.common.ListItems
import com.egoriku.grodnoroads.foundation.dialog.common.Title
import com.egoriku.grodnoroads.foundation.dialog.common.content.RadioButtonItem

@Composable
fun CommonReportDialog(
    titleRes: Int,
    actions: List<String>,
    onClose: () -> Unit,
    onSelected: (selected: Int, inputText: String) -> Unit
) {
    var selectedItem by remember { mutableStateOf(-1) }
    var inputText by remember { mutableStateOf("") }

    val sendButtonEnable by remember(selectedItem) {
        mutableStateOf(selectedItem != -1)
    }

    Dialog(onDismissRequest = onClose) {
        DialogContent {
            Title(titleRes = titleRes, center = true)

            ListItems(
                modifier = Modifier.weight(weight = 1f, fill = false),
                list = actions,
                onClick = { index, _ -> selectedItem = index },
                footer = {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        value = inputText,
                        singleLine = false,
                        onValueChange = { inputText = it },
                        label = { Text(stringResource(R.string.dialog_input_hint)) }
                    )
                }
            ) { index, item ->
                val selected = remember(selectedItem) { index == selectedItem }

                RadioButtonItem(
                    item = item,
                    index = index,
                    selected = selected,
                    onSelect = {
                        selectedItem = index
                    }
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                AlertButton(
                    modifier = Modifier.weight(1f),
                    textResId = R.string.cancel,
                    onClick = onClose
                )
                AlertButton(
                    enabled = sendButtonEnable,
                    modifier = Modifier.weight(1f),
                    textResId = R.string.send,
                    onClick = {
                        onSelected(selectedItem, inputText)
                    }
                )
            }
        }
    }
}