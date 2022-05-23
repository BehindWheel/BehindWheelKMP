package com.egoriku.grodnoroads.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActionResponse(
    @SerialName("type")
    val type: String,

    @SerialName("message")
    val message: String,

    @SerialName("latitude")
    val latitude: Double,

    @SerialName("longitude")
    val longitude: Double,

    @SerialName("added_time")
    val addedTime: Long
)