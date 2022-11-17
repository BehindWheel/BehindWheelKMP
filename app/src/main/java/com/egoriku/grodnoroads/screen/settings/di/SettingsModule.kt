package com.egoriku.grodnoroads.screen.settings.di

import com.egoriku.grodnoroads.screen.settings.SettingsComponent
import com.egoriku.grodnoroads.screen.settings.SettingsComponentImpl
import com.egoriku.grodnoroads.settings.alerts.di.alertsModule
import com.egoriku.grodnoroads.settings.appearance.di.appearanceModule
import com.egoriku.grodnoroads.settings.faq.di.faqModule
import com.egoriku.grodnoroads.settings.map.di.mapSettingsModule
import com.egoriku.grodnoroads.settings.whatsnew.di.whatsNewModule
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val settingsModule = module {

    includes(
        alertsModule,
        appearanceModule,
        faqModule,
        mapSettingsModule,
        whatsNewModule
    )

    factoryOf(::SettingsComponentImpl) { bind<SettingsComponent>() }
}