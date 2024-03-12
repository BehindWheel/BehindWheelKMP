package com.egoriku.grodnoroads.eventreporting.domain.repository

import com.egoriku.grodnoroads.eventreporting.domain.ReportActionModel

interface ReportingRepository {
    suspend fun report(actionModel: ReportActionModel)
}