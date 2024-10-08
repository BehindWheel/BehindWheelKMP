package com.egoriku.grodnoroads.foundation.common.ui.dialog.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.RadioButton

// TODO: use new design
@Composable
fun RadioButtonItem(
    item: String,
    index: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onSelect: (index: Int) -> Unit
) {
    ListItem(
        modifier = modifier
            .height(40.dp)
            .clickable { onSelect(index) },
        leadingContent = {
            RadioButton(
                selected = selected,
                onClick = {
                    onSelect(index)
                }
            )
        },
        headlineContent = {
            Text(
                item,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    )
}

@PreviewGrodnoRoads
@Composable
private fun PreviewRadioButtonItemPreview() = GrodnoRoadsM3ThemePreview {
    RadioButtonItem(
        item = "test",
        index = 0,
        selected = true,
        onSelect = {}
    )
}
