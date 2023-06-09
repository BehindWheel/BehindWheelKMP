package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview

@Composable
fun Checkbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Checkbox(
        colors = CheckboxDefaults.colors(
            checkedColor = MaterialTheme.colorScheme.secondary
        ),
        checked = isChecked,
        onCheckedChange = onCheckedChange
    )
}

@GrodnoRoadsPreview
@Composable
private fun PreviewCheckbox() = GrodnoRoadsM3ThemePreview {
    Column {
        Checkbox(
            isChecked = true,
            onCheckedChange = {}
        )
        Checkbox(
            isChecked = false,
            onCheckedChange = {}
        )
    }
}