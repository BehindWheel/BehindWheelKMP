package com.egoriku.grodnoroads.foundation.uikit.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R

@Composable
fun PrimaryButton(
    @StringRes id: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Text(text = stringResource(id))
    }
}

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick,
        content = content
    )
}


@GrodnoRoadsPreview
@Composable
private fun PrimaryButtonPreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.size(300.dp, 100.dp)) {
        PrimaryButton(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            id = R.string.app_name,
            onClick = {}
        )
    }
}