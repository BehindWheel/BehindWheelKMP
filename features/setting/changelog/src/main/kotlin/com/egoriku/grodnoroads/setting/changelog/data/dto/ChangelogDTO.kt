package com.egoriku.grodnoroads.setting.changelog.data.dto

import androidx.annotation.Keep
import com.google.firebase.firestore.PropertyName
import java.util.Date

@Keep
class ChangelogDTO(
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