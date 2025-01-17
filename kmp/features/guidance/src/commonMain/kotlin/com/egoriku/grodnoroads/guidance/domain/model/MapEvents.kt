package com.egoriku.grodnoroads.guidance.domain.model

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class MapEvents(
    val data: ImmutableList<MapEvent> = persistentListOf()
)
