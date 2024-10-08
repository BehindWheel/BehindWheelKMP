package com.egoriku.grodnoroads.screen.root.koin

import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory
import org.koin.dsl.module

val rootModule = module {
    factory {
        RootStoreFactory(
            storeFactory = get(),
            dataStore = get()
        ).create()
    }
}