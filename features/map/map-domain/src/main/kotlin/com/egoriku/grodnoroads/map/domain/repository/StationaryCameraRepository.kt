package com.egoriku.grodnoroads.map.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.StationaryCamera
import kotlinx.coroutines.flow.Flow

interface StationaryCameraRepository {

    fun loadAsFlow(): Flow<ResultOf<List<StationaryCamera>>>
}