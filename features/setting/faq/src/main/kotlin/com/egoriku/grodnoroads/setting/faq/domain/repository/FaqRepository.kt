package com.egoriku.grodnoroads.setting.faq.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.setting.faq.domain.model.FAQ

internal interface FaqRepository {

    suspend fun load(): ResultOf<List<FAQ>>
}