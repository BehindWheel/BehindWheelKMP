package com.egoriku.grodnoroads.eventreporting.domain.model

import com.egoriku.grodnoroads.shared.core.models.MapEventType

sealed interface ReportParams {
    data class EventReport(
        val mapEventType: MapEventType,
        val shortMessage: String,
        val message: String
    ) : ReportParams

    data class MobileCameraReport(
        val speedLimit: Int,
        val cameraInfo: String
    ) : ReportParams
}