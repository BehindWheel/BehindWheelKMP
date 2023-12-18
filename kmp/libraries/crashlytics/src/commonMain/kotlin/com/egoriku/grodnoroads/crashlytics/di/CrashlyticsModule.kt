package com.egoriku.grodnoroads.crashlytics.di

import com.egoriku.grodnoroads.crashlytics.CrashlyticsTracker
import com.egoriku.grodnoroads.crashlytics.CrashlyticsTrackerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val crashlytics = module {
    singleOf(::CrashlyticsTrackerImpl) { bind<CrashlyticsTracker>() }
}