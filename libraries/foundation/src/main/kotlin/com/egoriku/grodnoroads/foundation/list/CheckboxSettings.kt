package com.egoriku.grodnoroads.foundation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.Checkbox
import com.egoriku.grodnoroads.resources.R

@Composable
fun CheckboxSettings(
    iconRes: Int,
    stringResId: Int,
    isChecked: Boolean,
    leadingPaddingValues: PaddingValues = PaddingValues(),
    onCheckedChange: (Boolean) -> Unit = {}
) {
    BasicCheckboxSettings(
        iconRes = iconRes,
        leadingPaddingValues = leadingPaddingValues,
        stringResId = stringResId,
        isChecked = isChecked,
        onCheckedChange = onCheckedChange
    )
}

@Composable
fun CheckboxSettings(
    imageVector: ImageVector,
    stringResId: Int,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    BasicCheckboxSettings(
        imageVector = imageVector,
        stringResId = stringResId,
        isChecked = isChecked,
        onCheckedChange = onCheckedChange
    )
}

@Composable
private fun BasicCheckboxSettings(
    leadingPaddingValues: PaddingValues = PaddingValues(),
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
            ),
        leadingContent = {
            Checkbox(
                modifier = Modifier.padding(leadingPaddingValues),
                isChecked = isChecked,
                onCheckedChange = onCheckedChange
            )
        },
        headlineContent = {
            Text(text = stringResource(id = stringResId))
        },
        trailingContent = {
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
        }
    )
    Divider()
}

@GrodnoRoadsPreview
@Composable
private fun PreviewCheckboxSettings() = GrodnoRoadsM3ThemePreview {
    Column {
        CheckboxSettings(
            imageVector = Icons.Default.Settings,
            stringResId = R.string.app_name,
            isChecked = true,
            onCheckedChange = {}
        )
        CheckboxSettings(
            imageVector = Icons.Default.Settings,
            stringResId = R.string.app_name,
            isChecked = false,
            onCheckedChange = {}
        )
    }
}