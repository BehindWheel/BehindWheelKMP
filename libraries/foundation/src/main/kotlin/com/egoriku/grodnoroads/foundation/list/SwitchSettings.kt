package com.egoriku.grodnoroads.foundation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R

@Composable
fun SwitchSettings(
    iconRes: Int,
    stringResId: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    BasicSwitchSettings(
        iconRes = iconRes,
        stringResId = stringResId,
        isChecked = isChecked,
        onCheckedChange = onCheckedChange
    )
}

@Composable
fun SwitchSettings(
    imageVector: ImageVector,
    stringResId: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    BasicSwitchSettings(
        imageVector = imageVector,
        stringResId = stringResId,
        isChecked = isChecked,
        onCheckedChange = onCheckedChange
    )
}

@Composable
private fun BasicSwitchSettings(
    imageVector: ImageVector? = null,
    iconRes: Int? = null,
    stringResId: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    ListItem(
        modifier = Modifier
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = onCheckedChange
            )
            .padding(start = 8.dp),
        leadingContent = {
            if (imageVector != null) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = null
                )
            }
            if (iconRes != null) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null
                )
            }
        },
        headlineContent = {
            Text(text = stringResource(id = stringResId))
        },
        trailingContent = {
            val icon: (@Composable () -> Unit)? = if (isChecked) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
            Switch(
                modifier = Modifier.scale(0.9f),
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.onSecondary,
                    checkedTrackColor = MaterialTheme.colorScheme.secondary
                ),
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                thumbContent = icon
            )
        }
    )
    Divider()
}


@GrodnoRoadsPreview
@Composable
private fun PreviewSwitchSettings() = GrodnoRoadsM3ThemePreview {
    Column {
        SwitchSettings(
            imageVector = Icons.Default.Add,
            stringResId = R.string.app_name,
            isChecked = true,
            onCheckedChange = {}
        )
        SwitchSettings(
            imageVector = Icons.Default.Add,
            stringResId = R.string.app_name,
            isChecked = false,
            onCheckedChange = {}
        )
    }
}