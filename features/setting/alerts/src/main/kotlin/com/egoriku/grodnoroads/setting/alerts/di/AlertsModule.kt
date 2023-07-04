package com.egoriku.grodnoroads.setting.alerts.di

import com.egoriku.grodnoroads.setting.alerts.domain.store.AlertsStoreFactory
import org.koin.dsl.module

val alertsModule = module {
    factory {
        AlertsStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}