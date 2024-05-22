package com.egoriku.grodnoroads.mainflow.screen

import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.ic_map
import com.egoriku.grodnoroads.compose.resources.ic_settings
import com.egoriku.grodnoroads.localization.R
import org.jetbrains.compose.resources.DrawableResource

internal sealed class Screen(
    val index: Int,
    val labelId: Int,
    val drawableResource: DrawableResource
) {

    data object Map : Screen(
        index = 0,
        labelId = R.string.tab_map,
        drawableResource = Res.drawable.ic_map
    )

    data object AppSettings : Screen(
        index = 1,
        labelId = R.string.tab_settings,
        drawableResource = Res.drawable.ic_settings
    )
}