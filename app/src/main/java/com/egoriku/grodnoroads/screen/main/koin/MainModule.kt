package com.egoriku.grodnoroads.screen.main.koin

import com.arkivanov.decompose.ComponentContext
import com.egoriku.grodnoroads.screen.main.MainComponent
import com.egoriku.grodnoroads.screen.main.MainComponentImpl
import org.koin.dsl.module

val mainModule = module {
    factory<MainComponent> { (componentContext: ComponentContext) ->
        MainComponentImpl(componentContext = componentContext)
    }
}