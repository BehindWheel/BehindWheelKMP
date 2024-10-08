package com.egoriku.grodnoroads.quicksettings.di

import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsStoreFactory
import org.koin.dsl.module

val quickSettingsModule = module {
    factory {
        QuickSettingsStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}
