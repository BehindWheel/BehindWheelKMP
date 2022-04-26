package com.egoriku.grodnoroads.domain.repository

import com.egoriku.grodnoroads.data.response.StationaryCameraResponse

interface StationaryCameraRepository {
    suspend fun load(): List<StationaryCameraResponse>
}