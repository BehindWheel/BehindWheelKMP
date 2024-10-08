package com.egoriku.grodnoroads.foundation.uikit.listitem

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Brightness
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.Switch

@Composable
fun SwitchListItem(
    text: String,
    description: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
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
        if (imageVector != null) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(top = 8.dp),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                imageVector = imageVector,
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
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SwitchListItem(
    text: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    onCheckedChange: (Boolean) -> Unit
) {
    BasicListItem(
        touchModifier = Modifier.toggleable(
            value = isChecked,
            role = Role.Switch,
            onValueChange = onCheckedChange
        ),
        imageVector = imageVector,
        text = text
    ) {
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange
        )
    }
}

@PreviewGrodnoRoads
@Composable
private fun PreviewSwitchListItemPreview() = GrodnoRoadsM3ThemePreview {
    var isChecked1 by rememberMutableState { false }
    var isChecked2 by rememberMutableState { false }
    var isChecked3 by rememberMutableState { false }

    Column {
        SwitchListItem(
            imageVector = GrodnoRoads.Outlined.Brightness,
            text = "За рулем | Гродно",
            isChecked = isChecked1,
            onCheckedChange = { isChecked1 = it }
        )
        SwitchListItem(
            imageVector = GrodnoRoads.Outlined.Brightness,
            text = "За рулем | Гродно",
            description = "За рулем | Гродно",
            isChecked = isChecked2,
            onCheckedChange = { isChecked2 = it }
        )
        SwitchListItem(
            imageVector = GrodnoRoads.Outlined.Brightness,
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
