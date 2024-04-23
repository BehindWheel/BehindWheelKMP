package com.egoriku.grodnoroads.eventreporting.domain.repository

import com.egoriku.grodnoroads.shared.core.models.dto.MobileCameraDTO
import com.egoriku.grodnoroads.shared.core.models.dto.ReportsDTO

interface ReportingRepository {
    suspend fun reportEvent(reportsDTO: ReportsDTO)

    suspend fun reportMobileCamera(mobileCameraDTO: MobileCameraDTO)
}