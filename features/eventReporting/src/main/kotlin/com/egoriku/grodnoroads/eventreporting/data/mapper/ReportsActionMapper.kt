package com.egoriku.grodnoroads.eventreporting.data.mapper

import com.egoriku.grodnoroads.eventreporting.domain.ReportActionModel
import com.egoriku.grodnoroads.shared.core.models.Source
import com.egoriku.grodnoroads.shared.core.models.dto.ReportsDTO

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