package com.egoriku.grodnoroads.eventreporting.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.shared.models.dto.MobileCameraDTO
import com.egoriku.grodnoroads.shared.models.dto.ReportsDTO

interface ReportingRepository {
    suspend fun reportEvent(reportsDTO: ReportsDTO): ResultOf<Unit>

    suspend fun reportMobileCamera(mobileCameraDTO: MobileCameraDTO): ResultOf<Unit>
}
