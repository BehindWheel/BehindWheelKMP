package com.egoriku.grodnoroads

import android.graphics.Color as AndroidColor
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.defaultComponentContext
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3Theme
import com.egoriku.grodnoroads.foundation.theme.LocalPlatform
import com.egoriku.grodnoroads.foundation.theme.Platform.Android
import com.egoriku.grodnoroads.root.domain.AppTheme
import com.egoriku.grodnoroads.root.domain.buildRootComponent
import com.egoriku.grodnoroads.root.screen.RootContent

// Don't use ComponentActivity, due to it breaks language change
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition { true }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val root = buildRootComponent(defaultComponentContext())
        setContent {
            val appTheme by root.appTheme.collectAsState(null)

            LaunchedEffect(appTheme) {
                if (appTheme != null) {
                    splash.setKeepOnScreenCondition { false }
                }
            }

            appTheme?.let {
                val isDarkTheme = when (it) {
                    AppTheme.Dark -> true
                    AppTheme.Light -> false
                    AppTheme.System -> isSystemInDarkTheme()
                }

                DisposableEffect(isDarkTheme) {
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.auto(
                            lightScrim = AndroidColor.TRANSPARENT,
                            darkScrim = AndroidColor.TRANSPARENT
                        ) { isDarkTheme },
                        navigationBarStyle = SystemBarStyle.auto(
                            lightScrim = AndroidColor.TRANSPARENT,
                            darkScrim = AndroidColor.TRANSPARENT
                        ) { isDarkTheme }
                    )
                    onDispose {}
                }

                GrodnoRoadsM3Theme(isDarkTheme) {
                    CompositionLocalProvider(LocalPlatform provides Android) {
                        RootContent(root)
                    }
                }
            }
        }
    }
}
