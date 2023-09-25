package com.egoriku.grodnoroads.mapswrapper.core

import androidx.compose.runtime.Stable
import com.google.android.gms.maps.model.LatLng

@JvmInline
@Stable
value class StableLatLng(val value: LatLng)

fun LatLng.asStable() = StableLatLng(this)