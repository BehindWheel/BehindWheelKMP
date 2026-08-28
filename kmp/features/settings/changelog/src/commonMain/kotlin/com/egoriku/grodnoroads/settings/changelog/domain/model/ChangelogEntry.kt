package com.egoriku.grodnoroads.settings.changelog.domain.model

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Serializable
@Stable
data class ChangelogEntry(
    val id: String,
    val platform: ChangelogPlatform,
    val versionName: String,
    val notes: String,
    val releaseDateMillis: Long
) {

    fun latestReleaseBadge(emoji: String): String = "$versionName $emoji"
}
