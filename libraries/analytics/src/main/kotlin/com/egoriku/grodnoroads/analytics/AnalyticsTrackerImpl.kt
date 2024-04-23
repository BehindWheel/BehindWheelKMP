package com.egoriku.grodnoroads.analytics

import androidx.core.os.bundleOf
import com.egoriku.grodnoroads.analytics.AnalyticsEvent.EVENT_OPEN_MARKER_INFO_DIALOG
import com.egoriku.grodnoroads.analytics.AnalyticsEvent.EVENT_OPEN_ROAD_INCIDENT_DIALOG
import com.egoriku.grodnoroads.analytics.AnalyticsEvent.EVENT_OPEN_TRAFFIC_POLICE_DIALOG
import com.egoriku.grodnoroads.analytics.AnalyticsEvent.EVENT_REPORT_MOBILE_CAMERA
import com.egoriku.grodnoroads.analytics.AnalyticsEvent.PARAM_SHORT_MESSAGE
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

internal class AnalyticsTrackerImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsTracker {

    override fun trackOpenMarkerInfoDialog() =
        firebaseAnalytics.logEvent(EVENT_OPEN_MARKER_INFO_DIALOG, bundleOf())

    override fun trackOpenTrafficPoliceDialog() =
        firebaseAnalytics.logEvent(EVENT_OPEN_TRAFFIC_POLICE_DIALOG, bundleOf())

    override fun trackOpenRoadIncidentDialog() =
        firebaseAnalytics.logEvent(EVENT_OPEN_ROAD_INCIDENT_DIALOG, bundleOf())

    override fun eventReportAction(eventType: String, shortMessage: String) {
        firebaseAnalytics.logEvent(AnalyticsEvent.EVENT_REPORT_ACTION) {
            param(AnalyticsEvent.PARAM_EVENT_TYPE, eventType)
            param(PARAM_SHORT_MESSAGE, shortMessage)
        }
    }

    override fun mobileCameraReport() =
        firebaseAnalytics.logEvent(EVENT_REPORT_MOBILE_CAMERA, bundleOf())
}