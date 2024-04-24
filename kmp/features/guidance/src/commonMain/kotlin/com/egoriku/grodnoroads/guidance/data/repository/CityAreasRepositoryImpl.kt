package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.guidance.data.dto.areas.AreasDTO
import com.egoriku.grodnoroads.guidance.data.util.AreasGeoJsonAssetReader
import com.egoriku.grodnoroads.guidance.domain.model.area.Area
import com.egoriku.grodnoroads.guidance.domain.repository.CityAreasRepository
import com.egoriku.grodnoroads.location.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class CityAreasRepositoryImpl : CityAreasRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun load(): List<Area> = withContext(Dispatchers.Default) {
        val areas = AreasGeoJsonAssetReader.readAreasFile()
        val featureCollection = json.decodeFromString<AreasDTO>(areas)

        featureCollection.features.map {
            Area(
                name = it.properties.name,
                coordinates = it.geometry.coordinates.first().first().map { coordinates ->
                    LatLng(
                        latitude = coordinates[1],
                        longitude = coordinates[0]
                    )
                }
            )
        }
    }
}