package com.egoriku.grodnoroads.domain.repository

import com.egoriku.grodnoroads.data.model.StationaryResponse

interface StationaryCameraRepository {
    suspend fun load(): List<StationaryResponse>
}