package com.egoriku.grodnoroads.guidance.screen.ui.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun UsersCount(count: Int, modifier: Modifier) {
    UsersCountBadge(count = count, onClick = {}, modifier = modifier)
}
