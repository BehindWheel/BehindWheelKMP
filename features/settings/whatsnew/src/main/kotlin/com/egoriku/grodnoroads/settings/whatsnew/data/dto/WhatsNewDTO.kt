package com.egoriku.grodnoroads.settings.whatsnew.data.dto

import com.google.firebase.firestore.PropertyName
import java.util.Date

class WhatsNewDTO(
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