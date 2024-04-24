 package com.egoriku.grodnoroads.guidance.domain.model.area

import com.egoriku.grodnoroads.location.LatLng

data class Area(
    val name: String,
    val coordinates: List<LatLng>
)
