package com.egoriku.grodnoroads.shared.analytics

import com.egoriku.grodnoroads.shared.analytics.AnalyticsEvent.EVENT_OPEN_MARKER_INFO_DIALOG
import com.egoriku.grodnoroads.shared.analytics.AnalyticsEvent.EVENT_REPORT_MOBILE_CAMERA
import com.egoriku.grodnoroads.shared.analytics.AnalyticsEvent.PARAM_SHORT_MESSAGE
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import dev.gitlive.firebase.analytics.logEvent

internal class AnalyticsTrackerImpl(
    private val firebaseAnalytics: FirebaseAnalytics
) : AnalyticsTracker {

    override fun trackOpenMarkerInfoDialog() =
        firebaseAnalytics.logEvent(EVENT_OPEN_MARKER_INFO_DIALOG)

    override fun eventReportAction(eventType: String, shortMessage: String) {
        firebaseAnalytics.logEvent(AnalyticsEvent.EVENT_REPORT_ACTION) {
            param(AnalyticsEvent.PARAM_EVENT_TYPE, eventType)
            param(PARAM_SHORT_MESSAGE, shortMessage)
        }
    }

    override fun mobileCameraReport() =
        firebaseAnalytics.logEvent(EVENT_REPORT_MOBILE_CAMERA)
}