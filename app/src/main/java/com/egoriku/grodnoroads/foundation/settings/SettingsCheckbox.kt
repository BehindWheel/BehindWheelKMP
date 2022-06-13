package com.egoriku.grodnoroads.foundation.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Checkbox
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
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

@Composable
fun SettingsCheckbox(
    modifier: Modifier = Modifier,
    @DrawableRes iconId: Int,
    @StringRes titleId: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {},
) {
    SettingsCheckbox(
        modifier = modifier,
        title = {
            Text(text = stringResource(id = titleId))
        },
        icon = {
            Image(
                modifier = Modifier.size(36.dp),
                painter = painterResource(id = iconId),
                contentDescription = null
            )
        },
        isChecked = isChecked,
        onCheckedChange = onCheckedChange
    )
}
