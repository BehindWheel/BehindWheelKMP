package com.egoriku.grodnoroads.domain.repository

import com.egoriku.grodnoroads.data.response.ReportActionResponse
import kotlinx.coroutines.flow.Flow

interface ReportActionRepository {

    suspend fun report(reportActionResponse: ReportActionResponse)

    fun usersActions(): Flow<List<ReportActionResponse>>
}