package com.egoriku.grodnoroads.shared.analytics.di

import com.egoriku.grodnoroads.shared.analytics.AnalyticsTracker
import com.egoriku.grodnoroads.shared.analytics.AnalyticsTrackerImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val analyticsModule = module {
    singleOf(::AnalyticsTrackerImpl) { bind<AnalyticsTracker>() }
}
