package com.egoriku.grodnoroads.screen.map.data.model

import androidx.annotation.Keep
import com.google.firebase.database.PropertyName

@Keep
class MobileCameraResponse(
    @PropertyName("name")
    @JvmField
    val name: String = "",

    @PropertyName("latitude")
    @JvmField
    val latitude: Double = 0.0,

    @PropertyName("longitude")
    @JvmField
    val longitude: Double = 0.0
)