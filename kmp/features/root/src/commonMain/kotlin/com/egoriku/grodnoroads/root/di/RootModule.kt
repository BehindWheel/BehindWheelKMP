package com.egoriku.grodnoroads.root.di

import com.egoriku.grodnoroads.root.domain.RootStoreFactory
import org.koin.dsl.module

val rootModule = module {
    factory {
        RootStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}
