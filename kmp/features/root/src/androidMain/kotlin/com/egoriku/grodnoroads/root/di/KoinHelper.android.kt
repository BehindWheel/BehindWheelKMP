package com.egoriku.grodnoroads.root.di

import android.content.Context
import com.egoriku.grodnoroads.datastore.dataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(
    context: Context,
    additionalModule: Module
) {
    startKoin {
        androidContext(context)
        modules(appModule() + additionalModule)
    }
}

internal actual val platformDataStoreModule = module {
    single { get<Context>().dataStore() }
}