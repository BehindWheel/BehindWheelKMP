package com.egoriku.grodnoroads.eventreporting.data.mapper

import com.egoriku.grodnoroads.eventreporting.domain.model.ReportParams.EventReport
import com.egoriku.grodnoroads.eventreporting.domain.model.ReportParams.MobileCameraReport
import com.egoriku.grodnoroads.shared.core.models.Source
import com.egoriku.grodnoroads.shared.core.models.dto.MobileCameraDTO
import com.egoriku.grodnoroads.shared.core.models.dto.ReportsDTO
import com.google.android.gms.maps.model.LatLng

internal object ReportEventMapper : (LatLng, EventReport) -> ReportsDTO {

    private val currentTime: Long
        get() = System.currentTimeMillis()

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