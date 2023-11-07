package com.egoriku.grodnoroads.analytics.di

import com.egoriku.grodnoroads.analytics.AnalyticsTracker
import com.egoriku.grodnoroads.analytics.AnalyticsTrackerImpl
import com.google.firebase.analytics.analytics
import com.google.firebase.Firebase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val analyticsModule = module {

    single { Firebase.analytics }
    singleOf(::AnalyticsTrackerImpl) { bind<AnalyticsTracker>() }
}