package com.egoriku.grodnoroads.screen.settings.di

import com.egoriku.grodnoroads.screen.settings.SettingsComponent
import com.egoriku.grodnoroads.screen.settings.SettingsComponentImpl
import com.egoriku.grodnoroads.settings.faq.di.faqModule
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val settingsModule = module {

    includes(faqModule)

    factoryOf(::SettingsComponentImpl) { bind<SettingsComponent>() }
}