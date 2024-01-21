package com.egoriku.grodnoroads.setting.changelog.domain.model

data class ReleaseNotes(
    val versionCode: Int,
    val versionName: String,
    val notes: String,
    val releaseDate: String
)