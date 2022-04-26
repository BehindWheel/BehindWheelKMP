package com.egoriku.grodnoroads.domain.repository

import com.egoriku.grodnoroads.data.response.ReportActionResponse

interface ReportActionRepository {
    suspend fun report(reportActionResponse: ReportActionResponse)
}