package com.egoriku.grodnoroads.foundation.dialog.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxItem(
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
        Checkbox(
            checked = selected,
            onCheckedChange = {
                onSelect(index)
            },
        )
        Text(
            item,
            modifier = Modifier,
            color = MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.body1
        )
    }
}
