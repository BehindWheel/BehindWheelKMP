package com.egoriku.grodnoroads.data.repository

import com.egoriku.grodnoroads.data.api.GrodnoRoadsApi
import com.egoriku.grodnoroads.data.model.ActionResponse
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ReportActionRepositoryImpl(
    private val api: GrodnoRoadsApi
) : ReportActionRepository {

    override suspend fun report(actionResponse: ActionResponse) {
        api.sendReport(actionResponse)
    }

    override fun usersActions(): Flow<List<ActionResponse>> {
        return flow {
            emit(api.getRoadEvents())
        }
    }
}