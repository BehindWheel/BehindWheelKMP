package com.egoriku.grodnoroads.screen.settings.faq.data

import com.google.firebase.firestore.PropertyName

class FaqResponse(
    @PropertyName("q")
    @JvmField
    val question: String = "",

    @PropertyName("a")
    @JvmField
    val answer: String = ""
)