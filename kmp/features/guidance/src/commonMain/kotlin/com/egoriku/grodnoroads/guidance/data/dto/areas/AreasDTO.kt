package com.egoriku.grodnoroads.guidance.data.dto.areas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias MultiPolygon = List<List<List<List<Double>>>>

@Serializable
data class AreasDTO(
    val type: String,
    val features: List<Feature>
)

@Serializable
data class Feature(
    @SerialName("geometry")
    val geometry: MultiPolygonGeometry,

    @SerialName("properties")
    val properties: Properties
)

@Serializable
data class MultiPolygonGeometry(
    @SerialName("coordinates")
    val coordinates: MultiPolygon
)

@Serializable
data class Properties(
    @SerialName("name")
    val name: String
)