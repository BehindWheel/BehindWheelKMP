package com.egoriku.grodnoroads.foundation.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.button.AlertButton
import com.egoriku.grodnoroads.foundation.dialog.common.DialogContent
import com.egoriku.grodnoroads.foundation.dialog.common.ListItems
import com.egoriku.grodnoroads.foundation.dialog.common.Title
import com.egoriku.grodnoroads.foundation.dialog.common.content.CheckBoxItem
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun IncidentDialog(
    onClose: () -> Unit,
    onSelected: (selected: Int) -> Unit
) {
    var selectedItem by remember { mutableStateOf(-1) }

    val actions by remember {
        mutableStateOf(
            listOf(
                "ДТП",
                "Пробка",
                "Сломалась машина",
                "Ремонт дороги",
                "Не работают светофоры",
                "Дикие животные",
            )
        )
    }
    Dialog(onDismissRequest = onClose) {
        DialogContent {
            Title(titleRes = R.string.dialog_incident, center = true)

            ListItems(
                list = actions,
                onClick = { index, _ -> selectedItem = index },
            ) { index, item ->
                val selected = remember(selectedItem) { index == selectedItem }

                CheckBoxItem(
                    item = item,
                    index = index,
                    selected = selected,
                    onSelect = {
                        selectedItem = index
                    }
                )
            }

            var text by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                value = text,
                singleLine = false,
                onValueChange = { text = it },
                label = { Text("Опциональное сообщение") }
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                AlertButton(
                    modifier = Modifier.weight(1f),
                    textResId = R.string.cancel,
                    onClick = onClose
                )
                AlertButton(
                    modifier = Modifier.weight(1f),
                    textResId = R.string.send,
                    onClick = {
                        onSelected(selectedItem)
                    }
                )
            }
        }
    }
}


@Preview
@Preview(locale = "ru")
@Composable
fun PreviewIncidentDialog() {
    GrodnoRoadsTheme {
        IncidentDialog(onClose = {}, onSelected = {})
    }
}