package com.egoriku.grodnoroads.screen.settings.koin

import com.arkivanov.decompose.ComponentContext
import com.egoriku.grodnoroads.screen.settings.SettingsComponent
import com.egoriku.grodnoroads.screen.settings.SettingsComponentImpl
import org.koin.dsl.module

val settingsModule = module {
    factory<SettingsComponent> { (componentContext: ComponentContext) ->
        SettingsComponentImpl(componentContext = componentContext)
    }
}