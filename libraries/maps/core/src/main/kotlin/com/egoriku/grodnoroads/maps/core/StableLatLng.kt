package com.egoriku.grodnoroads.maps.core

import androidx.compose.runtime.Stable
import com.google.android.gms.maps.model.LatLng

@JvmInline
@Stable
value class StableLatLng(val value: LatLng) {

    constructor(latitude: Double, longitude: Double) : this(LatLng(latitude, longitude))
}

fun LatLng.asStable() = StableLatLng(this)