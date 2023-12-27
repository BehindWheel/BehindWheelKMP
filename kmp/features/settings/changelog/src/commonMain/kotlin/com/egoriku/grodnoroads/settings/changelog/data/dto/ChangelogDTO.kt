package com.egoriku.grodnoroads.settings.changelog.data.dto

import dev.gitlive.firebase.firestore.Timestamp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmField

@Serializable
internal class ChangelogDTO(
    @SerialName("name")
    @JvmField
    // TODO: remove JvmField
    val name: String,

    @SerialName("code")
    @JvmField
    val code: Int,

    @SerialName("notes")
    @JvmField
    val notes: String,

    @SerialName("date")
    @JvmField
    val releaseDate: Timestamp
)