package com.egoriku.grodnoroads.data.response

import com.google.firebase.database.PropertyName

class LatLngResponse(
    @PropertyName("lat")
    @JvmField
    val latitude: Double = 0.0,

    @PropertyName("lng")
    @JvmField
    val longitude: Double = 0.0
)