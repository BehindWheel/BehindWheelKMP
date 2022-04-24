package com.egoriku.grodnoroads.data

import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.PropertyName

class StationaryEntity(
    @PropertyName("message")
    @JvmField
    val message: String = "",

    @PropertyName("speed")
    @JvmField
    val speed: Int = 0,

    @PropertyName("position")
    @JvmField
    val position: GeoPoint = GeoPoint(0.0, 0.0)
)