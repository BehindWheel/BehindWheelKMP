package com.egoriku.grodnoroads.map.data.dto

import androidx.annotation.Keep
import com.google.firebase.database.PropertyName

@Keep
internal class MobileCameraDTO(
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
    val speed: Int = 0
)