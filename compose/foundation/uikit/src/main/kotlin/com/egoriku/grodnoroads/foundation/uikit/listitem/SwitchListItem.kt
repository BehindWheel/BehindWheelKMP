package com.egoriku.grodnoroads.foundation.uikit.listitem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.Switch
import com.egoriku.grodnoroads.shared.resources.MR

@Composable
fun SwitchListItem(
    @DrawableRes iconRes: Int? = null,
    text: String,
    description: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = isChecked,
                role = Role.Switch,
                onValueChange = onCheckedChange
            )
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (iconRes != null) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(top = 8.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                painter = painterResource(iconRes),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = LocalContentColor.current.copy(alpha = 0.64f)
            )
        }
        Switch(
            modifier = Modifier.align(Alignment.Top),
            checked = isChecked,
            onCheckedChange = onCheckedChange,
        )
    }
}

@Composable
fun SwitchListItem(
    iconRes: Int? = null,
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    BasicListItem(
        touchModifier = Modifier.toggleable(
            value = isChecked,
            role = Role.Switch,
            onValueChange = onCheckedChange
        ),
        iconRes = iconRes,
        text = text,
    ) {
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun PreviewSwitchListItem() = GrodnoRoadsM3ThemePreview {
    var isChecked1 by rememberMutableState { false }
    var isChecked2 by rememberMutableState { false }
    var isChecked3 by rememberMutableState { false }

    Column {
        SwitchListItem(
            iconRes = MR.images.ic_brightness.drawableResId,
            text = "За рулем | Гродно",
            isChecked = isChecked1,
            onCheckedChange = { isChecked1 = it }
        )
        SwitchListItem(
            iconRes = MR.images.ic_brightness.drawableResId,
            text = "За рулем | Гродно",
            description = "За рулем | Гродно",
            isChecked = isChecked2,
            onCheckedChange = { isChecked2 = it }
        )
        SwitchListItem(
            iconRes = MR.images.ic_brightness.drawableResId,
            text = "За рулем | Гродно",
            description = "За рулем | Гродно\nЗа рулем | Гродно",
            isChecked = isChecked3,
            onCheckedChange = { isChecked3 = it }
        )
        SwitchListItem(
            text = "За рулем | Гродно",
            description = "За рулем | Гродно",
            isChecked = isChecked3,
            onCheckedChange = { isChecked3 = it }
        )
    }
}