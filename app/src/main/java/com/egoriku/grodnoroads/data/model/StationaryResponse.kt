package com.egoriku.grodnoroads.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StationaryResponse(
    @SerialName("message")
    val message: String,

    @SerialName("speed")
    val speed: Int,

    @SerialName("latitude")
    val latitude: Double,

    @SerialName("longitude")
    val longitude: Double,

    @SerialName("last_update_time")
    val lastUpdateTime: Long
)