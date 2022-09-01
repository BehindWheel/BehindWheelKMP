package com.egoriku.grodnoroads.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.egoriku.grodnoroads.R

sealed class Screen(val index: Int, val icon: ImageVector, val labelId: Int) {

    object Map : Screen(index = 0, icon = Icons.Default.Explore, labelId = R.string.tab_map)
    object Settings : Screen(index = 1, icon = Icons.Default.Settings, labelId = R.string.tab_settings)
}