package com.egoriku.grodnoroads.guidance.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Reports
import kotlinx.coroutines.flow.Flow

interface ReportsRepository {

    fun loadAsFlow(): Flow<ResultOf<List<Reports>>>
}
