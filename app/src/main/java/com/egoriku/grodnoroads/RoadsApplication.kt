@file:Suppress("unused")

package com.egoriku.grodnoroads

import android.app.Application
import com.egoriku.grodnoroads.koin.koinModule
import com.egoriku.grodnoroads.koin.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RoadsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RoadsApplication)
            modules(koinModule, networkModule)
        }
    }
}