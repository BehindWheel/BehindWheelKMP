package com.egoriku.grodnoroads.map.domain.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.shared.core.models.Source

@Stable
data class MessageItem(
    val message: String,
    val source: Source
)