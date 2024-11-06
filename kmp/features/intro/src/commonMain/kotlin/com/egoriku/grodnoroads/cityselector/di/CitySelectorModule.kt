package com.egoriku.grodnoroads.cityselector.di

import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStoreFactory
import org.koin.dsl.module

internal val citySelectorModule = module {
    factory {
        CitySelectorStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}
