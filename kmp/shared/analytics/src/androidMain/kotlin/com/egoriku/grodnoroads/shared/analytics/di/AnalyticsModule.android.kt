package com.egoriku.grodnoroads.shared.analytics.di

import com.egoriku.grodnoroads.shared.analytics.AnalyticsTracker
import com.egoriku.grodnoroads.shared.analytics.AnalyticsTrackerImpl
import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val analyticsModule = module {
    single { Firebase.analytics }
    singleOf(::AnalyticsTrackerImpl) { bind<AnalyticsTracker>() }
}