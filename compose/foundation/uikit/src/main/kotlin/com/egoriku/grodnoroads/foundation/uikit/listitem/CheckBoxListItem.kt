package com.egoriku.grodnoroads.foundation.uikit.listitem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.HorizontalSpacer
import com.egoriku.grodnoroads.foundation.uikit.checkbox.Checkbox
import com.egoriku.grodnoroads.shared.resources.MR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckBoxListItem(
    @DrawableRes iconRes: Int,
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
        HorizontalSpacer(12.dp)
        Image(
            modifier = Modifier.size(iconSize),
            painter = painterResource(iconRes),
            contentDescription = null
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun CheckBoxListItemPreview() = GrodnoRoadsM3ThemePreview {
    var state by rememberMutableState { true }

    CheckBoxListItem(
        text = "Мобильная камера",
        isChecked = state,
        iconRes = MR.images.nt_ic_mobile_camera.drawableResId,
        onCheckedChange = { state = it }
    )
}