package com.egoriku.grodnoroads.guidance.domain.repository

import com.egoriku.grodnoroads.guidance.domain.model.area.Area

interface CityAreasRepository {

    suspend fun load(): List<Area>
}