package com.egoriku.grodnoroads.screen.settings.alerts.di

import com.egoriku.grodnoroads.screen.settings.alerts.domain.store.AlertsStoreFactory
import org.koin.dsl.module

val alertsModule = module {
    factory {
        AlertsStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}