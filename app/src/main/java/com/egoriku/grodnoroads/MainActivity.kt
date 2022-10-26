package com.egoriku.grodnoroads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.screen.root.RoadsRootComponentImpl
import com.egoriku.grodnoroads.screen.root.RootContent
import com.egoriku.grodnoroads.shared.appsettings.extension.dataStore
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.appTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val context = LocalContext.current
            val preferences = runBlocking { context.dataStore.data.first() }

            val root by remember {
                mutableStateOf(RoadsRootComponentImpl(defaultComponentContext()))
            }

            //TODO add None theme and wait from datastore real data
            val theme by context.appTheme.collectAsState(initial = preferences.appTheme)

            val darkTheme = when (theme) {
                Theme.System -> isSystemInDarkTheme()
                Theme.Dark -> true
                Theme.Light -> false
            }

            GrodnoRoadsTheme(darkTheme) {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = MaterialTheme.colors.isLight

                DisposableEffect(systemUiController, useDarkIcons) {
                    systemUiController.setStatusBarColor(
                        color = Color.Transparent,
                        darkIcons = useDarkIcons
                    )

                    onDispose {}
                }
                RootContent(roadsRootComponent = root)
            }
        }
    }
}