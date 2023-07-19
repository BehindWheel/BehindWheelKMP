package com.egoriku.grodnoroads

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.egoriku.grodnoroads.extensions.common.StateData
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent
import com.egoriku.grodnoroads.screen.root.RoadsRootComponentImpl
import com.egoriku.grodnoroads.screen.root.RootContent
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme
import com.egoriku.grodnoroads.util.LocalWindowSizeClass
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()

        splash.setKeepOnScreenCondition { true }

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

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

                    GrodnoRoadsM3Theme(darkTheme) {
                        val systemUiController = rememberSystemUiController()

                        DisposableEffect(systemUiController, darkTheme) {
                            systemUiController.apply {
                                setSystemBarsColor(color = Color.Transparent, darkIcons = darkTheme)
                                isNavigationBarContrastEnforced = false
                                statusBarDarkContentEnabled = !darkTheme
                                navigationBarDarkContentEnabled = !darkTheme
                            }
                            onDispose {}
                        }
                        CompositionLocalProvider(
                            LocalWindowSizeClass provides calculateWindowSizeClass(this),
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