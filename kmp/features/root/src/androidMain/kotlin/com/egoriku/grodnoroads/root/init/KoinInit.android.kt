package com.egoriku.grodnoroads.root.init

import android.content.Context
import com.egoriku.grodnoroads.root.di.appModule
import org.koin.android.ext.koin.androidContext

actual object KoinInit {

    fun start(context: Context) {
        startKoinOnce {
            androidContext(context)
            modules(appModule())
        }
    }
}
