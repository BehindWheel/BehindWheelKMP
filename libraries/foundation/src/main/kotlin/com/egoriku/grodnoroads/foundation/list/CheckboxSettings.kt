package com.egoriku.grodnoroads.foundation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R

@Composable
fun CheckboxSettings(
    iconRes: Int,
    stringResId: Int,
    isChecked: Boolean,
    useTint: Boolean = false,
    leadingPaddingValues: PaddingValues = PaddingValues(),
    onCheckedChange: (Boolean) -> Unit = {}
) {
    BasicCheckboxSettings(
        iconRes = iconRes,
        leadingPaddingValues = leadingPaddingValues,
        stringResId = stringResId,
        isChecked = isChecked,
        useTint = useTint,
        onCheckedChange = onCheckedChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BasicCheckboxSettings(
    iconRes: Int,
    stringResId: Int,
    isChecked: Boolean,
    useTint: Boolean = false,
    leadingPaddingValues: PaddingValues = PaddingValues(),
    onCheckedChange: (Boolean) -> Unit = {}
) {
    ListItem(
        modifier = Modifier
            .height(48.dp)
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = onCheckedChange
            ),
        leadingContent = {
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                Checkbox(
                    modifier = Modifier.padding(leadingPaddingValues),
                    checked = isChecked,
                    onCheckedChange = onCheckedChange
                )
            }
        },
        headlineContent = {
            Text(text = stringResource(id = stringResId))
        },
        trailingContent = {
            if (useTint) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null
                )
            } else {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null
                )
            }
        }
    )
}

@GrodnoRoadsPreview
@Composable
private fun PreviewCheckboxSettings() = GrodnoRoadsM3ThemePreview {
    Column {
        CheckboxSettings(
            iconRes = R.drawable.ic_settings_traffic_jam,
            stringResId = R.string.app_name,
            isChecked = true,
            onCheckedChange = {}
        )
        CheckboxSettings(
            iconRes = R.drawable.ic_settings_traffic_jam,
            stringResId = R.string.app_name,
            isChecked = false,
            onCheckedChange = {}
        )
    }
}