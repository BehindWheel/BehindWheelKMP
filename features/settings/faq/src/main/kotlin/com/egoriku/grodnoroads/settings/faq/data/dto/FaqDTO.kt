package com.egoriku.grodnoroads.settings.faq.data.dto

import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName

@Keep
internal class FaqDTO(
    @PropertyName("q")
    @JvmField
    val question: String = "",

    @PropertyName("a")
    @JvmField
    val answer: String = ""
)