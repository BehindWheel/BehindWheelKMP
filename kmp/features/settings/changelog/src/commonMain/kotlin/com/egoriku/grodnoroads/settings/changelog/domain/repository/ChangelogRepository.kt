package com.egoriku.grodnoroads.settings.changelog.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogEntry
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.model.NewChangelogEntry

interface ChangelogRepository {

    suspend fun load(platform: ChangelogPlatform): ResultOf<List<ChangelogEntry>>
    suspend fun add(entry: NewChangelogEntry, platform: ChangelogPlatform): ResultOf<ChangelogEntry>
    suspend fun update(id: String, entry: NewChangelogEntry, platform: ChangelogPlatform): ResultOf<ChangelogEntry>
    suspend fun delete(id: String): ResultOf<Unit>
}
