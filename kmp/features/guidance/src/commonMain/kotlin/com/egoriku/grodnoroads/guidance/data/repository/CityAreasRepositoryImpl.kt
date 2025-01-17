package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.guidance.data.dto.areas.AreaDTO
import com.egoriku.grodnoroads.guidance.domain.model.area.Area
import com.egoriku.grodnoroads.guidance.domain.repository.CityAreasRepository
import com.egoriku.grodnoroads.location.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

class CityAreasRepositoryImpl : CityAreasRepository {

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalResourceApi::class)
    override suspend fun load(): List<Area> = withContext(Dispatchers.Default) {
        val areasJson = Res.readBytes("files/areas.geojson").decodeToString()
        val areas = json.decodeFromString<List<AreaDTO>>(areasJson)

        areas.map {
            Area(
                name = it.name,
                coordinates = it.coordinates.map { latLng ->
                    LatLng(
                        latitude = latLng.latitude,
                        longitude = latLng.longitude
                    )
                }
            )
        }
    }
}
