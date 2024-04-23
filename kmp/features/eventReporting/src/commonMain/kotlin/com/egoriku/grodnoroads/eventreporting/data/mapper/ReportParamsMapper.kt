package com.egoriku.grodnoroads.eventreporting.data.mapper

import com.egoriku.grodnoroads.shared.models.reporting.ReportParams.EventReport
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams.MobileCameraReport
import com.egoriku.grodnoroads.extensions.DateTime
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.shared.models.Source
import com.egoriku.grodnoroads.shared.models.dto.MobileCameraDTO
import com.egoriku.grodnoroads.shared.models.dto.ReportsDTO

internal object ReportEventMapper : (LatLng, EventReport) -> ReportsDTO {

    private val currentTime: Long
        get() = DateTime.currentTimeMillis()

    override fun invoke(latLng: LatLng, eventReport: EventReport): ReportsDTO {
        return ReportsDTO(
            timestamp = currentTime,
            type = eventReport.mapEventType.type,
            message = eventReport.message,
            shortMessage = eventReport.shortMessage,
            latitude = latLng.latitude,
            longitude = latLng.longitude,
            source = Source.App.source
        )
    }
}

internal object MobileCameraReportMapper : (LatLng, MobileCameraReport) -> MobileCameraDTO {

    override fun invoke(latLng: LatLng, cameraReport: MobileCameraReport): MobileCameraDTO {
        return MobileCameraDTO(
            id = "",
            name = cameraReport.cameraInfo,
            latitude = latLng.latitude,
            longitude = latLng.longitude,
            speed = cameraReport.speedLimit,
            angle = -1f,
            bidirectional = true
        )
    }
}