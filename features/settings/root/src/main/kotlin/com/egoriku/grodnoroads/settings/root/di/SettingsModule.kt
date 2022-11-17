package com.egoriku.grodnoroads.settings.root.di

import com.egoriku.grodnoroads.settings.alerts.di.alertsModule
import com.egoriku.grodnoroads.settings.appearance.di.appearanceModule
import com.egoriku.grodnoroads.settings.faq.di.faqModule
import com.egoriku.grodnoroads.settings.map.di.mapSettingsModule
import com.egoriku.grodnoroads.settings.root.domain.component.SettingsComponent
import com.egoriku.grodnoroads.settings.root.domain.component.SettingsComponentImpl
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