package com.egoriku.grodnoroads

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.egoriku.grodnoroads.extensions.common.StateData
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent
import com.egoriku.grodnoroads.screen.root.RoadsRootComponentImpl
import com.egoriku.grodnoroads.screen.root.RootContent
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()

        splash.setKeepOnScreenCondition { true }

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val root: RoadsRootComponent by remember {
                mutableStateOf(RoadsRootComponentImpl(defaultComponentContext()))
            }

            val themeState by root.themeState.collectAsState(initial = StateData.Idle)

            when (val theme = themeState) {
                is StateData.Loaded -> {
                    splash.setKeepOnScreenCondition { false }
                    val darkTheme = when (theme.data) {
                        Theme.System -> isSystemInDarkTheme()
                        Theme.Dark -> true
                        Theme.Light -> false
                    }

                    GrodnoRoadsTheme(darkTheme) {
                        val systemUiController = rememberSystemUiController()
                        val useDarkIcons = MaterialTheme.colors.isLight

                        DisposableEffect(systemUiController, useDarkIcons) {
                            systemUiController.setSystemBarsColor(
                                color = Color.Transparent,
                                darkIcons = useDarkIcons
                            )

                            onDispose {}
                        }
                        RootContent(roadsRootComponent = root)
                    }
                }

                else -> {}
            }
        }
    }
}