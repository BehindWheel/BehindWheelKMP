package com.egoriku.grodnoroads.setting.whatsnew.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.setting.whatsnew.domain.model.ReleaseNotes

interface WhatsNewRepository {

    suspend fun load(): ResultOf<List<ReleaseNotes>>
}