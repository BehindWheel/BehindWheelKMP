package com.egoriku.grodnoroads.domain.repository

import com.egoriku.grodnoroads.data.model.ActionResponse
import kotlinx.coroutines.flow.Flow

interface ActionRepository {

    suspend fun report(actionResponse: ActionResponse)

    fun usersActions(): Flow<List<ActionResponse>>
}