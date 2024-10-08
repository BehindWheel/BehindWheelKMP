package com.egoriku.grodnoroads.foundation.uikit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun Switch(
    checked: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
)
