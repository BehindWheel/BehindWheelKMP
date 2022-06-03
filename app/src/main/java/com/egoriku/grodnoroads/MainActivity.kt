package com.egoriku.grodnoroads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.egoriku.grodnoroads.screen.root.RoadsRootComponentImpl
import com.egoriku.grodnoroads.screen.root.RootContent
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory
import com.egoriku.grodnoroads.screen.settings.domain.Theme
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val root by remember {
                mutableStateOf(RoadsRootComponentImpl(defaultComponentContext()))
            }

            val themeState by root.themeState.collectAsState(initial = RootStoreFactory.State())

            val darkTheme = when (themeState.theme) {
                Theme.System -> isSystemInDarkTheme()
                Theme.Dark -> true
                Theme.Light -> false
            }

            GrodnoRoadsTheme(darkTheme = darkTheme) {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colors.isLight

                SideEffect {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons
                    )
                }
                RootContent(roadsRootComponent = root)
            }
        }
    }
}