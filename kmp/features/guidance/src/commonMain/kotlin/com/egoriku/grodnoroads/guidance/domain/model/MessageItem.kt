package com.egoriku.grodnoroads.guidance.domain.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.shared.models.MessageSource

@Stable
data class MessageItem(
    val message: String,
    val messageSource: MessageSource
)