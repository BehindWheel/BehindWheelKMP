package com.egoriku.grodnoroads.shared.core.models.dto

import androidx.annotation.Keep
import com.google.firebase.database.PropertyName

@Keep
class MobileCameraDTO(
    @PropertyName("id")
    @JvmField
    val id: String = "",

    @PropertyName("name")
    @JvmField
    val name: String = "",

    @PropertyName("latitude")
    @JvmField
    val latitude: Double = 0.0,

    @PropertyName("longitude")
    @JvmField
    val longitude: Double = 0.0,

    @PropertyName("speed")
    @JvmField
    val speed: Int = 0,

    @PropertyName("angle")
    @JvmField
    val angle: Float = 0.0f,

    @PropertyName("bidirectional")
    @JvmField
    val bidirectional: Boolean = false,
)