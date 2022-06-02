package com.egoriku.grodnoroads.foundation.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.egoriku.grodnoroads.foundation.button.AlertTextButton
import com.egoriku.grodnoroads.foundation.dialog.common.DialogContent
import com.egoriku.grodnoroads.foundation.dialog.common.ListItems

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

                SingleChoiceItem(
                    item = item,
                    index = index,
                    selected = selected,
                    onSelect = {
                        selectedItem = index
                    }
                )
            }

            Row(modifier = Modifier.fillMaxWidth()) {
                AlertTextButton(
                    modifier = Modifier.weight(1f),
                    textResId = android.R.string.cancel,
                    onClick = onClose
                )
                AlertTextButton(
                    modifier = Modifier.weight(1f),
                    textResId = android.R.string.ok,
                    onClick = {
                        onSelected(selectedItem)
                    }
                )
            }
        }
    }
}

@Composable
private fun SingleChoiceItem(
    item: String,
    index: Int,
    selected: Boolean,
    onSelect: (index: Int) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onSelect(index) }
            .padding(start = 12.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                onSelect(index)
            },
        )
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(32.dp)
        )
        Text(
            item,
            modifier = Modifier,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListSingleChoiceDialog() {
    ListSingleChoiceDialog(
        list = listOf("System", "Dark", "Light"),
        initialSelection = 0,
        onClose = {},
        onSelected = {}
    )
}