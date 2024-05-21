package com.egoriku.grodnoroads.shared.models.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MobileCameraDTO(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("latitude")
    val latitude: Double,

    @SerialName("longitude")
    val longitude: Double,

    @SerialName("speed")
    val speed: Int,

    @SerialName("angle")
    val angle: Float,

    @SerialName("bidirectional")
    val bidirectional: Boolean
)