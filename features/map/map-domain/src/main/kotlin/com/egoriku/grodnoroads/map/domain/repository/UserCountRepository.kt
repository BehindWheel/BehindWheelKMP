package com.egoriku.grodnoroads.map.domain.repository

import com.egoriku.grodnoroads.extensions.common.ResultOf
import kotlinx.coroutines.flow.Flow

interface UserCountRepository {

    fun loadAsFlow(): Flow<ResultOf<Int>>
}