package com.egoriku.grodnoroads.settings.faq.data.dto

import com.google.firebase.firestore.PropertyName

internal class FaqDTO(
    @PropertyName("q")
    @JvmField
    val question: String = "",

    @PropertyName("a")
    @JvmField
    val answer: String = ""
)