package com.egoriku.grodnoroads.analytics

interface AnalyticsTracker {

    fun trackOpenMarkerInfoDialog()
    fun trackOpenTrafficPoliceDialog()
    fun trackOpenRoadIncidentDialog()

    fun eventReportAction(eventType: String, shortMessage: String)
}