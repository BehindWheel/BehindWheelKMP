package com.egoriku.grodnoroads.foundation.dialog.common.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RadioButtonItem(
    item: String,
    index: Int,
    selected: Boolean,
    onSelect: (index: Int) -> Unit
) {
    ListItem(
        modifier = Modifier
            .height(48.dp)
            .clickable { onSelect(index) },
        icon = {
            RadioButton(
                selected = selected,
                onClick = {
                    onSelect(index)
                },
            )
        },
        text = {
            Text(
                item,
                color = MaterialTheme.colors.onSurface,
            )
        }
    )
}

@Preview
@Composable
private fun PreviewRadioButtonItem() {
    GrodnoRoadsTheme {
        RadioButtonItem(
            item = "test",
            index = 0,
            selected = true,
            onSelect = {}
        )
    }
}