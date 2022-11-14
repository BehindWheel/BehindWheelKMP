package com.egoriku.grodnoroads.foundation.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.egoriku.grodnoroads.foundation.dialog.content.DialogButton
import com.egoriku.grodnoroads.foundation.dialog.content.RadioButtonItem
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.resources.R

@Composable
fun ListSingleChoiceDialog(
    list: List<String>,
    initialSelection: Int,
    onClose: () -> Unit,
    onSelected: (selected: Int) -> Unit
) {
    var selectedItem by remember { mutableStateOf(initialSelection) }

    Dialog(onDismissRequest = onClose) {
        DialogContent {
            ListItems(
                modifier = Modifier.padding(vertical = 16.dp),
                list = list,
                onClick = { index, _ -> selectedItem = index },
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
                DialogButton(
                    modifier = Modifier.weight(1f),
                    textResId = R.string.cancel,
                    onClick = onClose
                )
                DialogButton(
                    modifier = Modifier.weight(1f),
                    textResId = R.string.ok,
                    onClick = {
                        onSelected(selectedItem)
                    }
                )
            }
        }
    }
}

@GrodnoRoadsPreview
@Composable
fun PreviewListSingleChoiceDialog() {
    GrodnoRoadsTheme {
        ListSingleChoiceDialog(
            list = listOf("System", "Dark", "Light"),
            initialSelection = 0,
            onClose = {},
            onSelected = {}
        )
    }
}