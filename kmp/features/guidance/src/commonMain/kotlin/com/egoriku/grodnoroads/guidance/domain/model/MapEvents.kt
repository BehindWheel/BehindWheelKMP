package com.egoriku.grodnoroads.guidance.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class MapEvents(
    val data: List<MapEvent> = emptyList()
)
