package com.egoriku.grodnoroads.settings.changelog.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogEntry
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform

interface ChangelogRepository {

    suspend fun load(platform: ChangelogPlatform): ResultOf<List<ChangelogEntry>>
}
