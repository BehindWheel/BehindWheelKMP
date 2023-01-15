package com.egoriku.grodnoroads.foundation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
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
        icon = {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = null
            )
        },
        text = {
            Text(text = stringResource(id = stringResId))
        },
        trailing = {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        }
    )
    Divider()
}

@OptIn(ExperimentalMaterialApi::class)
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
        icon = {
            Image(
                imageVector = imageVector,
                contentDescription = null
            )
        },
        text = {
            Text(text = stringResource(id = stringResId))
        },
        trailing = {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        }
    )
    Divider()
}