package com.egoriku.grodnoroads.crashlytics.di

import com.egoriku.grodnoroads.crashlytics.CrashlyticsTracker
import com.egoriku.grodnoroads.crashlytics.CrashlyticsTrackerImpl
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val crashlyticsModule = module {

    single { Firebase.crashlytics }
    singleOf(::CrashlyticsTrackerImpl) { bind<CrashlyticsTracker>() }
}