package com.egoriku.grodnoroads.foundation.uikit

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R

@Composable
fun OutlinedButton(
    @StringRes id: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(8.dp),
        border = ButtonDefaults.outlinedButtonBorder.copy(
            width = 2.dp,
            brush = SolidColor(MaterialTheme.colorScheme.outline)
        ),
        onClick = onClick
    ) {
        Text(text = stringResource(id))
    }
}

@GrodnoRoadsPreview
@Composable
private fun OutlinedButtonPreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.size(300.dp, 100.dp)) {
        OutlinedButton(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            id = R.string.app_name,
            onClick = {}
        )
    }
}