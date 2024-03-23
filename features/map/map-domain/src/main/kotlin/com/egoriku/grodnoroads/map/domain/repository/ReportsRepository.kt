package com.egoriku.grodnoroads.map.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import kotlinx.coroutines.flow.Flow

interface ReportsRepository {

    fun loadAsFlow(startAt: Long): Flow<ResultOf<List<Reports>>>
}