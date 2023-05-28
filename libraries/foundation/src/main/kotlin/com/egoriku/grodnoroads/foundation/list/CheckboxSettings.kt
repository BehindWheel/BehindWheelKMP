package com.egoriku.grodnoroads.foundation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun CheckboxSettings(
    iconRes: Int,
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
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null
            )
        },
        headlineContent = {
            Text(text = stringResource(id = stringResId))
        },
        trailingContent = {
            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.secondary
                ),
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        }
    )
    Divider()
}

@Composable
fun CheckboxSettings(
    imageVector: ImageVector,
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
            Icon(
                imageVector = imageVector,
                contentDescription = null
            )
        },
        headlineContent = {
            Text(text = stringResource(id = stringResId))
        },
        trailingContent = {
            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.secondary
                ),
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        }
    )
    Divider()
}