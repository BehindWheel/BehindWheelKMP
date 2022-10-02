package com.egoriku.grodnoroads.map.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.map.domain.model.report.ReportActionModel
import kotlinx.coroutines.flow.Flow

interface ReportsRepository {

    fun loadAsFlow(): Flow<ResultOf<List<Reports>>>

    suspend fun report(actionModel: ReportActionModel)
}