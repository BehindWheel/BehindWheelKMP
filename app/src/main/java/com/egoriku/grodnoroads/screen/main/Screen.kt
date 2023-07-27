package com.egoriku.grodnoroads.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.egoriku.grodnoroads.resources.R

sealed class Screen(val index: Int, val icon: ImageVector, val labelId: Int) {

    data object Map : Screen(
        index = 0,
        icon = Icons.Default.Explore,
        labelId = R.string.tab_map
    )
    data object Settings : Screen(
        index = 1,
        icon = Icons.Default.Settings,
        labelId = R.string.tab_settings
    )
}