package com.egoriku.grodnoroads.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActionResponse(
    @Json(name = "type")
    val type: String,

    @Json(name = "message")
    val message: String,

    @Json(name = "latitude")
    val latitude: Double,

    @Json(name = "longitude")
    val longitude: Double,

    @Json(name = "added_time")
    val addedTime: Long
)