package com.egoriku.grodnoroads.settings.changelog.domain.model

import androidx.compose.runtime.Stable

@Stable
data class ChangelogEntry(
    val versionName: String,
    val notes: String,
    val releaseDate: String
) {

    fun latestReleaseBadge(emoji: String): String = "$versionName $emoji"
}
