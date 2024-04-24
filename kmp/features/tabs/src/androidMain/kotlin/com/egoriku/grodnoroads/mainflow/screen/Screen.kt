package com.egoriku.grodnoroads.mainflow.screen

import com.egoriku.grodnoroads.resources.MR
import com.egoriku.grodnoroads.resources_old.R

internal sealed class Screen(
    val index: Int,
    val labelId: Int,
    val iconRes: Int
) {

    data object Map : Screen(
        index = 0,
        labelId = MR.strings.tab_map.resourceId,
        iconRes = R.drawable.ic_map
    )

    data object AppSettings : Screen(
        index = 1,
        labelId = MR.strings.tab_settings.resourceId,
        iconRes = R.drawable.ic_settings
    )
}