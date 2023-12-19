package com.egoriku.grodnoroads.settings.faq.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
internal class FaqDTO(
    @SerialName("q")
    @JvmField
    val question: String,

    @SerialName("a")
    @JvmField
    val answer: String
)