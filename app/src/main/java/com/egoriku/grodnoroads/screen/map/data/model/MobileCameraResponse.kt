package com.egoriku.grodnoroads.screen.map.data.model

import com.google.firebase.database.PropertyName

class MobileCameraResponse(
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