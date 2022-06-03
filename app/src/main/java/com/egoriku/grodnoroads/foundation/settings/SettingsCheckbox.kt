package com.egoriku.grodnoroads.foundation.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import com.egoriku.grodnoroads.foundation.settings.common.SettingsTileAction
import com.egoriku.grodnoroads.foundation.settings.common.SettingsTileIcon
import com.egoriku.grodnoroads.foundation.settings.common.SettingsTileText

@Composable
fun SettingsCheckbox(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    Surface {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .toggleable(
                    value = isChecked,
                    role = Role.Checkbox,
                    onValueChange = onCheckedChange
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SettingsTileIcon(icon = icon)
            SettingsTileText(title)
            SettingsTileAction {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange
                )
            }
        }
    }
}