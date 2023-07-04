package com.egoriku.grodnoroads.setting.map.di

import com.egoriku.grodnoroads.setting.map.domain.store.MapSettingsStoreFactory
import org.koin.dsl.module

val mapSettingsModule = module {
    factory {
        MapSettingsStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}