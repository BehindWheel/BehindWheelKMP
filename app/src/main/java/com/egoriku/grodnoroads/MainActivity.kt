package com.egoriku.grodnoroads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.defaultComponentContext
import com.egoriku.grodnoroads.extensions.common.StateData
import com.egoriku.grodnoroads.foundation.core.LocalActivity
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent
import com.egoriku.grodnoroads.screen.root.RoadsRootComponentImpl
import com.egoriku.grodnoroads.screen.root.RootContent
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme
import com.egoriku.grodnoroads.util.LocalWindowSizeClass

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()

        splash.setKeepOnScreenCondition { true }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val root: RoadsRootComponent = RoadsRootComponentImpl(defaultComponentContext())

        setContent {
            val themeState by root.themeState.collectAsState(initial = StateData.Idle)

            when (val theme = themeState) {
                is StateData.Loaded -> {
                    splash.setKeepOnScreenCondition { false }
                    val darkTheme = when (theme.data) {
                        Theme.System -> isSystemInDarkTheme()
                        Theme.Dark -> true
                        Theme.Light -> false
                    }

                    DisposableEffect(darkTheme) {
                        enableEdgeToEdge(
                            statusBarStyle = SystemBarStyle.auto(
                                lightScrim = android.graphics.Color.TRANSPARENT,
                                darkScrim = android.graphics.Color.TRANSPARENT,
                            ) { darkTheme },
                            navigationBarStyle = SystemBarStyle.auto(
                                lightScrim = android.graphics.Color.TRANSPARENT,
                                darkScrim = android.graphics.Color.TRANSPARENT,
                            ) { darkTheme },
                        )
                        onDispose {}
                    }

                    GrodnoRoadsM3Theme(darkTheme) {
                        CompositionLocalProvider(
                            LocalWindowSizeClass provides calculateWindowSizeClass(this),
                            LocalActivity provides this,
                        ) {
                            RootContent(roadsRootComponent = root)
                        }
                    }
                }

                else -> {}
            }
        }
    }
}