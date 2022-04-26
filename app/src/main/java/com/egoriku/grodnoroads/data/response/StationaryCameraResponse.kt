package com.egoriku.grodnoroads.data.response

import com.google.firebase.database.PropertyName

class StationaryCameraResponse(
    @PropertyName("message")
    @JvmField
    val message: String = "",

    @PropertyName("speed")
    @JvmField
    val speed: Int = 0,

    @PropertyName("position")
    @JvmField
    val position: LatLngResponse = LatLngResponse()
)