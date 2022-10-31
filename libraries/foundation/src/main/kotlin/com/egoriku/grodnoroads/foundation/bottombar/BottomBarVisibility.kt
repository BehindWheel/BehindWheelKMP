package com.egoriku.grodnoroads.foundation.bottombar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf

val LocalBottomBarVisibilityController =
    staticCompositionLocalOf<(BottomBarVisibilityState) -> Unit> { {} }

@Composable
fun BottomBarVisibility(state: BottomBarVisibilityState) {
    val bottomBarVisibilityController = LocalBottomBarVisibilityController.current

    LaunchedEffect(Unit) {
        bottomBarVisibilityController(state)
    }
}

enum class BottomBarVisibilityState {
    SHOWN, HIDDEN;

    fun isShown() = this == SHOWN
}