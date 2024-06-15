package com.egoriku.grodnoroads.mainflow.screen

import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.ic_map
import com.egoriku.grodnoroads.compose.resources.ic_settings
import com.egoriku.grodnoroads.compose.resources.tab_map
import com.egoriku.grodnoroads.compose.resources.tab_settings
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

internal sealed class Screen(
    val index: Int,
    val stringResource: StringResource,
    val drawableResource: DrawableResource
) {

    data object Map : Screen(
        index = 0,
        stringResource = Res.string.tab_map,
        drawableResource = Res.drawable.ic_map
    )

    data object AppSettings : Screen(
        index = 1,
        stringResource = Res.string.tab_settings,
        drawableResource = Res.drawable.ic_settings
    )
}