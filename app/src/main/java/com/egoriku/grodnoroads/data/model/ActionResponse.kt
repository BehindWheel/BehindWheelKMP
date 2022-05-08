package com.egoriku.grodnoroads.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@JsonClass(generateAdapter = true)
data class ActionResponse(
    @SerialName("type")
    @Json(name = "type")
    val type: String,

    @SerialName("message")
    @Json(name = "message")
    val message: String,

    @SerialName("latitude")
    @Json(name = "latitude")
    val latitude: Double,

    @SerialName("longitude")
    @Json(name = "longitude")
    val longitude: Double,

    @SerialName("added_time")
    @Json(name = "added_time")
    val addedTime: Long
)