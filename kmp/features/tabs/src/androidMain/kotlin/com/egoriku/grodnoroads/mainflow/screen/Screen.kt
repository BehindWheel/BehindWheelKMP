package com.egoriku.grodnoroads.mainflow.screen

import com.egoriku.grodnoroads.shared.resources.MR
import com.egoriku.grodnoroads.localization.R

internal sealed class Screen(
    val index: Int,
    val labelId: Int,
    val iconRes: Int
) {

    data object Map : Screen(
        index = 0,
        labelId = R.string.tab_map,
        iconRes = MR.images.ic_map.drawableResId
    )

    data object AppSettings : Screen(
        index = 1,
        labelId = R.string.tab_settings,
        iconRes = MR.images.ic_settings.drawableResId
    )
}