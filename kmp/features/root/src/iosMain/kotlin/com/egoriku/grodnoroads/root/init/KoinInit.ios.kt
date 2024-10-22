package com.egoriku.grodnoroads.root.init

import com.egoriku.grodnoroads.root.di.appModule

actual object KoinInit {

    fun start() {
        startKoinOnce {
            modules(modules = appModule())
        }
    }
}
