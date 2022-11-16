package com.egoriku.grodnoroads.settings.map.di

import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponentImpl
import com.egoriku.grodnoroads.settings.map.domain.store.MapSettingsStoreFactory
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mapSettingsModule = module {
    factory {
        MapSettingsStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }

    factoryOf(::MapSettingsComponentImpl) { bind<MapSettingsComponent>() }
}