package com.egoriku.grodnoroads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.defaultComponentContext
import com.egoriku.grodnoroads.foundation.core.LocalActivity
import com.egoriku.grodnoroads.foundation.core.LocalWindowSizeClass
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.root.domain.RootComponentImpl
import com.egoriku.grodnoroads.root.ui.RootContent

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()

        splash.setKeepOnScreenCondition { true }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        splash.setKeepOnScreenCondition { false }

        val root = RootComponentImpl(defaultComponentContext())
        setContent {
            GrodnoRoadsM3Theme {
                CompositionLocalProvider(
                    LocalWindowSizeClass provides calculateWindowSizeClass(this),
                    LocalActivity provides this,
                ) {
                    RootContent(root)
                }
            }
        }
        /*val root: RoadsRootComponent = RoadsRootComponentImpl(defaultComponentContext())

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
                            RootContent(rootComponent = root)
                        }
                    }
                }

                else -> {}
            }
        }*/
    }
}