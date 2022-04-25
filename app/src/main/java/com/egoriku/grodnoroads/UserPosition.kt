package com.egoriku.grodnoroads

import com.google.android.gms.maps.model.LatLng

data class UserPosition(
    val location: LatLng,
    val bearing: Float
)