package com.egoriku.grodnoroads.domain.model

import com.google.android.gms.maps.model.LatLng

data class MapEvent(
    val type: UserActionType,
    val time: String,
    val message: String,
    val position: LatLng
)