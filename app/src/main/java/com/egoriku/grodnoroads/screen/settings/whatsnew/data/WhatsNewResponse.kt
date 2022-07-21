package com.egoriku.grodnoroads.screen.settings.whatsnew.data

import com.google.firebase.firestore.PropertyName

class WhatsNewResponse(
    @PropertyName("version")
    @JvmField
    val version: String = "",

    @PropertyName("notes")
    @JvmField
    val notes: String = ""
)