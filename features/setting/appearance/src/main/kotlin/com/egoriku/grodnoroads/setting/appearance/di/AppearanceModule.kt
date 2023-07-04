package com.egoriku.grodnoroads.setting.appearance.di

import com.egoriku.grodnoroads.setting.appearance.domain.store.AppearanceStoreFactory
import org.koin.dsl.module

val appearanceModule = module {
    factory {
        AppearanceStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}