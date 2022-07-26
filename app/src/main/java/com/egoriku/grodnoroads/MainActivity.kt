package com.egoriku.grodnoroads

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.egoriku.grodnoroads.common.datastore.DataFlow.appLanguage
import com.egoriku.grodnoroads.common.datastore.DataFlow.appTheme
import com.egoriku.grodnoroads.common.datastore.DataFlow.language
import com.egoriku.grodnoroads.common.datastore.dataStore
import com.egoriku.grodnoroads.screen.root.RoadsRootComponentImpl
import com.egoriku.grodnoroads.screen.root.RootContent
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Theme
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : LocalizedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val context = LocalContext.current
            val preferences = runBlocking { context.dataStore.data.first() }

            val root by remember {
                mutableStateOf(RoadsRootComponentImpl(defaultComponentContext()))
            }

            val theme by context.appTheme.collectAsState(initial = preferences.appTheme)
            val language by context.appLanguage.collectAsState(initial = preferences.language)

            val darkTheme = when (theme) {
                Theme.System -> isSystemInDarkTheme()
                Theme.Dark -> true
                Theme.Light -> false
            }

            GrodnoRoadsTheme(darkTheme = darkTheme, language = language) {
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