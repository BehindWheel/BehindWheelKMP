package com.egoriku.grodnoroads.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StationaryResponse(
    @Json(name = "message")
    val message: String,

    @Json(name = "speed")
    val speed: Int,

    @Json(name = "latitude")
    val latitude: Double,

    @Json(name = "longitude")
    val longitude: Double,

    @Json(name = "last_update_time")
    val lastUpdateTime: Long
)