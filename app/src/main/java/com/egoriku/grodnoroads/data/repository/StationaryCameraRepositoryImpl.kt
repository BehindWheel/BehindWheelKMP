package com.egoriku.grodnoroads.data.repository

import com.egoriku.grodnoroads.data.api.GrodnoRoadsApi
import com.egoriku.grodnoroads.domain.repository.StationaryCameraRepository

internal class StationaryCameraRepositoryImpl(
    private val api: GrodnoRoadsApi
) : StationaryCameraRepository {

    override suspend fun load() = api.getStationaryCameras()
}