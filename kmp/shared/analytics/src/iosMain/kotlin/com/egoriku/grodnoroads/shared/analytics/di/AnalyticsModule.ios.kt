package com.egoriku.grodnoroads.shared.analytics.di

import com.egoriku.grodnoroads.shared.analytics.AnalyticsTracker
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val analyticsModule = module {
    singleOf(::AnalyticsTrackerImpl) { bind<AnalyticsTracker>() }
}