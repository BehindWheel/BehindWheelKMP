package com.egoriku.grodnoroads.crashlytics.di

import com.egoriku.grodnoroads.crashlytics.CrashlyticsTracker
import com.egoriku.grodnoroads.crashlytics.CrashlyticsTrackerImpl
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val crashlyticsModule = module {

    single { Firebase.crashlytics }
    singleOf(::CrashlyticsTrackerImpl) { bind<CrashlyticsTracker>() }
}