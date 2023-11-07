package com.egoriku.grodnoroads.maps.compose.extension

import com.google.android.gms.maps.GoogleMap

val GoogleMap.zoom: Float
    get() = cameraPosition.zoom