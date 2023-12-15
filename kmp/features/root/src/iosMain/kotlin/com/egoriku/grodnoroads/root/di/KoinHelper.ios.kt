package com.egoriku.grodnoroads.root.di

import com.egoriku.grodnoroads.datastore.dataStore
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(modules = appModule())
    }
}

internal actual val platformDataStoreModule = module {
    single { dataStore() }
}