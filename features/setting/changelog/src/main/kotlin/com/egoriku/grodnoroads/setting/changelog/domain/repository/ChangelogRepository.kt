package com.egoriku.grodnoroads.setting.changelog.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.setting.changelog.domain.model.ReleaseNotes

interface ChangelogRepository {

    suspend fun load(): ResultOf<List<ReleaseNotes>>
}