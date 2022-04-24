package com.egoriku.grodnoroads.domain.repository

import com.egoriku.grodnoroads.data.StationaryEntity

interface StationaryCameraRepository {
    suspend fun load(): List<StationaryEntity>
}