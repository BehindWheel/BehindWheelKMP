package com.egoriku.grodnoroads.root.di

import android.content.Context
import com.egoriku.grodnoroads.datastore.dataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(context: Context) {
    startKoin {
        androidContext(context)
        modules(appModule())
    }
}

internal actual val platformDataStoreModule = module {
    single { get<Context>().dataStore() }
}