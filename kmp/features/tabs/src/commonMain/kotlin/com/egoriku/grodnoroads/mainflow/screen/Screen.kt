package com.egoriku.grodnoroads.mainflow.screen

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.tab_map
import com.egoriku.grodnoroads.compose.resources.tab_settings
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Map
import com.egoriku.grodnoroads.foundation.icons.outlined.Settings
import org.jetbrains.compose.resources.StringResource

internal sealed class Screen(
    val index: Int,
    val stringResource: StringResource,
    val imageVector: ImageVector,
    val size: Dp = 24.dp
) {

    data object Map : Screen(
        index = 0,
        stringResource = Res.string.tab_map,
        imageVector = GrodnoRoads.Outlined.Map,
        size = 22.dp
    )

    data object AppSettings : Screen(
        index = 1,
        stringResource = Res.string.tab_settings,
        imageVector = GrodnoRoads.Outlined.Settings
    )
}
