package com.egoriku.grodnoroads.shared.analytics

internal object AnalyticsEvent {

    const val EVENT_REPORT_ACTION = "event_report_action"
    const val EVENT_OPEN_MARKER_INFO_DIALOG = "event_open_marker_info_dialog"
    const val EVENT_REPORT_MOBILE_CAMERA = "event_report_mobile_camera"
    const val EVENT_IN_APP_UPDATE_ERROR = "event_in_app_update_error"

    const val PARAM_EVENT_TYPE = "param_event_type"
    const val PARAM_SHORT_MESSAGE = "param_short_message"
    const val PARAM_ERROR_CODE = "param_error_code"
    const val PARAM_ERROR_MESSAGE = "param_error_message"
}
