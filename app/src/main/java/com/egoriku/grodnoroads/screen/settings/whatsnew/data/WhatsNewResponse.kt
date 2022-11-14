package com.egoriku.grodnoroads.screen.settings.whatsnew.data

import com.google.firebase.firestore.PropertyName
import java.util.Date

class WhatsNewResponse(
    @PropertyName("name")
    @JvmField
    val name: String = "",

    @PropertyName("code")
    @JvmField
    val code: Int = 0,

    @PropertyName("notes")
    @JvmField
    val notes: String = "",

    @PropertyName("date")
    @JvmField
    val releaseDate: Date = Date()
)