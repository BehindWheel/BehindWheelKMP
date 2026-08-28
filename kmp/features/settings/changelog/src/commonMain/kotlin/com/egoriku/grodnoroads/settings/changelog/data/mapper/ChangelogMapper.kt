package com.egoriku.grodnoroads.settings.changelog.data.mapper

import com.egoriku.grodnoroads.settings.changelog.data.dto.ChangelogDTO
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogEntry
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.model.NewChangelogEntry
import dev.gitlive.firebase.firestore.Timestamp
import dev.gitlive.firebase.firestore.fromMilliseconds
import dev.gitlive.firebase.firestore.toMilliseconds

internal fun ChangelogDTO.toEntry(id: String, platform: ChangelogPlatform): ChangelogEntry = ChangelogEntry(
    id = id,
    platform = platform,
    versionName = name,
    notes = notes,
    releaseDateMillis = releaseDate.toMilliseconds().toLong()
)

internal fun NewChangelogEntry.toDTO(platform: ChangelogPlatform): ChangelogDTO = ChangelogDTO(
    name = versionName,
    notes = notes,
    releaseDate = Timestamp.fromMilliseconds(releaseDateMillis.toDouble()),
    platform = platform.query
)
