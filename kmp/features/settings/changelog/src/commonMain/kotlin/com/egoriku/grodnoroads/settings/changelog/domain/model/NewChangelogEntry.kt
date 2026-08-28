package com.egoriku.grodnoroads.settings.changelog.domain.model

data class NewChangelogEntry(
    val versionName: String,
    val notes: String,
    val releaseDateMillis: Long
)
