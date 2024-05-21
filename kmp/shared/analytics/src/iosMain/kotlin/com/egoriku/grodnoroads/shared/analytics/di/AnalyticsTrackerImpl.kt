package com.egoriku.grodnoroads.shared.analytics.di

import com.egoriku.grodnoroads.shared.analytics.AnalyticsTracker

class AnalyticsTrackerImpl : AnalyticsTracker {

    // TODO: make iOS implementation
    override fun trackOpenMarkerInfoDialog() = Unit
    override fun eventReportAction(eventType: String, shortMessage: String) = Unit
    override fun mobileCameraReport() = Unit
}