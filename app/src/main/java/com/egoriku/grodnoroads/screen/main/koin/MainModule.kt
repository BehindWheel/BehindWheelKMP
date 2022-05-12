package com.egoriku.grodnoroads.screen.main.koin

import com.egoriku.grodnoroads.screen.main.MainComponent
import com.egoriku.grodnoroads.screen.main.MainComponentImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mainModule = module {
    factoryOf(::MainComponentImpl) { bind<MainComponent>() }
}