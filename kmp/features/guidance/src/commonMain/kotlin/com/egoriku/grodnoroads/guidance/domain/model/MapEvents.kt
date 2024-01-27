package com.egoriku.grodnoroads.guidance.domain.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MapEvents(
    val data: ImmutableList<MapEvent> = persistentListOf()
)
