package com.egoriku.grodnoroads.kmp

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
import com.egoriku.grodnoroads.root.di.initKoin
import com.egoriku.grodnoroads.root.domain.RootComponentImpl
import com.egoriku.grodnoroads.root.ui.RootContent
import com.egoriku.grodnoroads.shared.components.AppBuildConfig
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        initKoin(
            context = applicationContext,
            additionalModule = module {
                singleOf(::AppBuildConfigImpl) { bind<AppBuildConfig>() }
            }
        )
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
    }
}