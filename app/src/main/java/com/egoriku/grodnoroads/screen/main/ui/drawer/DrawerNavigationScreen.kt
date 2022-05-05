package com.egoriku.grodnoroads.screen.main.ui.drawer

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.egoriku.grodnoroads.R

sealed class DrawerNavigationScreen(
    @StringRes val resourceId: Int,
    val imageVector: ImageVector
) {
    object Settings : DrawerNavigationScreen(
        resourceId = R.string.settings,
        imageVector = Icons.Filled.Settings
    )
}