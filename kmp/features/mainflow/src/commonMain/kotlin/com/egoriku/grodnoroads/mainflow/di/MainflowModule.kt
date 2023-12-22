package com.egoriku.grodnoroads.mainflow.di

import com.egoriku.grodnoroads.settings.alerts.di.alertsModule
import com.egoriku.grodnoroads.settings.appearance.di.appearanceModule
import com.egoriku.grodnoroads.settings.changelog.domain.di.changelogModule
import com.egoriku.grodnoroads.settings.faq.di.faqModule
import org.koin.dsl.module

val mainFlowModule = module {
    includes(
        alertsModule,
        appearanceModule,
        changelogModule,
        faqModule
    )
}