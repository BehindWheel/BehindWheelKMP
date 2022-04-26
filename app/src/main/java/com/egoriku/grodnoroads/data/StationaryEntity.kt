package com.egoriku.grodnoroads.data

import com.google.firebase.database.PropertyName

class StationaryEntity(
    @PropertyName("message")
    @JvmField
    val message: String = "",

    @PropertyName("speed")
    @JvmField
    val speed: Int = 0,

    @PropertyName("position")
    @JvmField
    val position: LatLng = LatLng()
)

class LatLng(
    @PropertyName("lat")
    @JvmField
    val latitude: Double = 0.0,

    @PropertyName("lng")
    @JvmField
    val longitude: Double = 0.0
)