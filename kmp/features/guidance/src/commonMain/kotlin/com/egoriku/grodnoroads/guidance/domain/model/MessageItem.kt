package com.egoriku.grodnoroads.guidance.domain.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.shared.models.Source

@Stable
data class MessageItem(
    val message: String,
    val source: Source
)