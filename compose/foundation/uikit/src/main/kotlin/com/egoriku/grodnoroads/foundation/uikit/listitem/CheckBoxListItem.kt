package com.egoriku.grodnoroads.foundation.uikit.listitem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.HorizontalSpacer
import com.egoriku.grodnoroads.foundation.uikit.checkbox.Checkbox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckBoxListItem(
    imageVector: ImageVector? = null,
    @DrawableRes iconRes: Int? = null,
    iconSize: DpSize = DpSize(24.dp, 24.dp),
    paddingValues: PaddingValues = PaddingValues(),
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = onCheckedChange
            )
            .padding(paddingValues)
            .padding(start = 6.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides true) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
        }
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(1f),
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
        if (imageVector != null) {
            HorizontalSpacer(12.dp)
            Image(
                modifier = Modifier.size(iconSize),
                imageVector = imageVector,
                contentDescription = null
            )
        }
        if (iconRes != null) {
            HorizontalSpacer(12.dp)
            Image(
                modifier = Modifier.size(iconSize),
                painter = painterResource(iconRes),
                contentDescription = null
            )
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun CheckBoxListItemPreview() = GrodnoRoadsM3ThemePreview {
    var state by rememberMutableState { true }

    CheckBoxListItem(
        text = "За рулем | Гродно",
        isChecked = state,
        imageVector = Icons.Default.Brightness7,
        onCheckedChange = { state = it }
    )
}