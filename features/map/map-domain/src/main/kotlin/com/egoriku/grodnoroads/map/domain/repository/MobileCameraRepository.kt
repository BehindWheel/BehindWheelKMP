package com.egoriku.grodnoroads.map.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.MobileCamera
import kotlinx.coroutines.flow.Flow

interface MobileCameraRepository {

    fun loadAsFlow(): Flow<ResultOf<List<MobileCamera>>>
}