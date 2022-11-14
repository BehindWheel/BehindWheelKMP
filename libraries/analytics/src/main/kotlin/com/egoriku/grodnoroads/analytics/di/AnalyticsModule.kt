package com.egoriku.grodnoroads.analytics.di

import com.egoriku.grodnoroads.analytics.AnalyticsTracker
import com.egoriku.grodnoroads.analytics.AnalyticsTrackerImpl
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val analyticsModule = module {

    single { Firebase.analytics }
    singleOf(::AnalyticsTrackerImpl) { bind<AnalyticsTracker>() }
}