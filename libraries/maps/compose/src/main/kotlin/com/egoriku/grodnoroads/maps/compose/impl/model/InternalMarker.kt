package com.egoriku.grodnoroads.maps.compose.impl.model

import com.google.android.gms.maps.model.Marker

data class InternalMarker(
    val marker: Marker,
    val onClick: () -> Unit = { },
)