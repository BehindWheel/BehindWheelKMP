package com.egoriku.grodnoroads.root.init

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.KoinApplicationDslMarker
import org.koin.mp.KoinPlatformTools

expect object KoinInit

@KoinApplicationDslMarker
fun startKoinOnce(appDeclaration: KoinApplication.() -> Unit) {
    if (KoinPlatformTools.defaultContext().getOrNull() == null) {
        startKoin(appDeclaration)
    }
}
