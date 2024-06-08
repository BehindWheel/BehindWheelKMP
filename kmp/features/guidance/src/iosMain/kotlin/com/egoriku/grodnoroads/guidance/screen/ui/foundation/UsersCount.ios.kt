package com.egoriku.grodnoroads.guidance.screen.ui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun UsersCount(modifier: Modifier, count: Int) {
    UsersCountBadge(count = count, onClick = {}, modifier = modifier)
}