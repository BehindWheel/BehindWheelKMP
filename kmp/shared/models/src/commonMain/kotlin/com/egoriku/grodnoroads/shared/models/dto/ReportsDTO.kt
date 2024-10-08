package com.egoriku.grodnoroads.shared.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ReportsDTO(
    @SerialName("type")
    val type: String,

    @SerialName("short_message")
    val shortMessage: String,

    @SerialName("message")
    val message: String,

    @SerialName("latitude")
    val latitude: Double,

    @SerialName("longitude")
    val longitude: Double,

    @SerialName("timestamp")
    val timestamp: Long,

    @SerialName("source")
    val source: String
)
