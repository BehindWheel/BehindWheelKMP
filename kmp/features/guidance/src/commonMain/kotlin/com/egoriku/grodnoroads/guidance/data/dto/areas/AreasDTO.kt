package com.egoriku.grodnoroads.guidance.data.dto.areas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AreaDTO(
    @SerialName("name")
    val name: String,
    @SerialName("coordinates")
    val coordinates: List<LatLng>
)

@Serializable
data class LatLng(
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double
)