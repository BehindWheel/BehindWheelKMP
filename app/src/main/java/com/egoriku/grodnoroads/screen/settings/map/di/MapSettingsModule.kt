package com.egoriku.grodnoroads.screen.settings.map.di

import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStoreFactory
import org.koin.dsl.module

val mapSettingsModule = module {
    factory {
        MapSettingsStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}