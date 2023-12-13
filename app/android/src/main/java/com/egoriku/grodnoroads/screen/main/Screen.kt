package com.egoriku.grodnoroads.screen.main

import com.egoriku.grodnoroads.resources.R

sealed class Screen(
    val index: Int,
    val labelId: Int,
    val iconRes: Int
) {

    data object Map : Screen(
        index = 0,
        labelId = R.string.tab_map,
        iconRes = R.drawable.ic_map
    )

    data object Settings : Screen(
        index = 1,
        labelId = R.string.tab_settings,
        iconRes = R.drawable.ic_settings
    )
}