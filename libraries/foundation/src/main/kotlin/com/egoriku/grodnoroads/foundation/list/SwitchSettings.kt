package com.egoriku.grodnoroads.foundation.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.SwitchOld
import com.egoriku.grodnoroads.resources.R

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
fun SwitchSettings(
    stringResId: Int,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    ListItem(
        modifier = modifier
            .height(48.dp)
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = onCheckedChange
            ),
        headlineContent = {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = stringResId),
            )
        },
        trailingContent = {
            SwitchOld(
                isChecked = isChecked,
                onCheckedChange = onCheckedChange,
            )
        }
    )
}

@Composable
fun SwitchSettings(
    imageVector: ImageVector,
    stringResId: Int,
    supportingResId: Int,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    ListItem(
        modifier = modifier
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
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = stringResId),
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = supportingResId),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        trailingContent = {
            SwitchOld(
                isChecked = isChecked,
                onCheckedChange = onCheckedChange,
            )
        }
    )
}


@Composable
fun SwitchSettings(
    stringResId: Int,
    supportingResId: Int,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    ListItem(
        modifier = modifier
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = onCheckedChange
            ),
        headlineContent = {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = stringResId),
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = stringResource(id = supportingResId),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        trailingContent = {
            SwitchOld(
                isChecked = isChecked,
                onCheckedChange = onCheckedChange,
            )
        }
    )
}


@Composable
private fun BasicSwitchSettings(
    imageVector: ImageVector? = null,
    stringResId: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    ListItem(
        modifier = Modifier
            .height(48.dp)
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
        },
        headlineContent = {
            Text(
                text = stringResource(id = stringResId),
                style = MaterialTheme.typography.titleMedium
            )
        },
        trailingContent = {
            SwitchOld(
                isChecked = isChecked,
                onCheckedChange = onCheckedChange,
            )
        }
    )
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
        SwitchSettings(
            stringResId = R.string.app_name,
            isChecked = false,
            onCheckedChange = {}
        )
        SwitchSettings(
            stringResId = R.string.app_name,
            supportingResId = R.string.app_name,
            isChecked = false,
            onCheckedChange = {}
        )
    }
}