package com.egoriku.grodnoroads.shared.analytics

interface AnalyticsTracker {

    fun trackOpenMarkerInfoDialog()

    fun eventReportAction(eventType: String, shortMessage: String)
    fun mobileCameraReport()
}
