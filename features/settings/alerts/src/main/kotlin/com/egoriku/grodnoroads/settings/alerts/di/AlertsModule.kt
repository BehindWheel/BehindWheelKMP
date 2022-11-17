package com.egoriku.grodnoroads.settings.alerts.di

import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponentImpl
import com.egoriku.grodnoroads.settings.alerts.domain.store.AlertsStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val alertsModule = module {
    factory {
        AlertsStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }

    factoryOf(::AlertsComponentImpl) { bind<AlertsComponent>() }
}