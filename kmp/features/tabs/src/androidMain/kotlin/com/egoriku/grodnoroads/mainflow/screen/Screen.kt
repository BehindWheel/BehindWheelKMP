package com.egoriku.grodnoroads.mainflow.screen

import com.egoriku.grodnoroads.resources.MR

internal sealed class Screen(
    val index: Int,
    val labelId: Int,
    val iconRes: Int
) {

    data object Map : Screen(
        index = 0,
        labelId = MR.strings.tab_map.resourceId,
        iconRes = MR.images.ic_map.drawableResId
    )

    data object AppSettings : Screen(
        index = 1,
        labelId = MR.strings.tab_settings.resourceId,
        iconRes = MR.images.ic_settings.drawableResId
    )
}