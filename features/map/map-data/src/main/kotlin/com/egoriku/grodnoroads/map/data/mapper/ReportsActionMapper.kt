package com.egoriku.grodnoroads.map.data.mapper

import com.egoriku.grodnoroads.map.data.dto.ReportsDTO
import com.egoriku.grodnoroads.map.domain.model.Source
import com.egoriku.grodnoroads.map.domain.model.report.ReportActionModel

internal object ReportsActionMapper : (ReportActionModel) -> ReportsDTO {

    override fun invoke(params: ReportActionModel): ReportsDTO {
        return ReportsDTO(
            timestamp = params.timestamp,
            type = params.type,
            message = params.message,
            shortMessage = params.shortMessage,
            latitude = params.latitude,
            longitude = params.longitude,
            source = Source.App.source
        )
    }
}