package com.egoriku.grodnoroads.root.screen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackGestureOverlay
import com.arkivanov.essenty.backhandler.BackDispatcher
import com.egoriku.grodnoroads.foundation.core.LocalWindowSizeClass
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.root.domain.RootComponent
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme

object RootViewController {

    @OptIn(
        ExperimentalMaterial3WindowSizeClassApi::class,
        ExperimentalComposeApi::class,
        ExperimentalDecomposeApi::class
    )
    fun create(
        rootComponent: RootComponent,
        backDispatcher: BackDispatcher
    ) = ComposeUIViewController(configure = { platformLayers = false }) {
        val theme by rootComponent.theme.collectAsState()

        theme?.let {
            val isDarkTheme = when (it) {
                Theme.System -> isSystemInDarkTheme()
                Theme.Dark -> true
                Theme.Light -> false
            }

            PredictiveBackGestureOverlay(
                backDispatcher = backDispatcher,
                backIcon = null,
                modifier = Modifier.fillMaxSize(),
            ) {
                GrodnoRoadsM3Theme(isDarkTheme) {
                    CompositionLocalProvider(
                        LocalWindowSizeClass provides calculateWindowSizeClass(),
                    ) {
                        RootContent(rootComponent)
                    }
                }
            }
        }
    }
}