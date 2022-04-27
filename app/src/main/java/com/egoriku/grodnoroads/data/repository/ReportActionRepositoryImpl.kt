package com.egoriku.grodnoroads.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.egoriku.grodnoroads.data.api.GrodnoRoadsApi
import com.egoriku.grodnoroads.data.model.ActionResponse
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import com.egoriku.grodnoroads.extension.logD
import com.egoriku.grodnoroads.preferences.NEW_TOPIC
import com.egoriku.grodnoroads.preferences.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class ReportActionRepositoryImpl(
    private val context: Context,
    private val api: GrodnoRoadsApi
) : ReportActionRepository {

    private val topicFlow = context.dataStore.data
        .map { it[NEW_TOPIC] ?: false }

    override suspend fun report(actionResponse: ActionResponse) {
        api.sendReport(actionResponse)
    }

    override fun usersActions(): Flow<List<ActionResponse>> = flow {
        emit(api.getRoadEvents())

        topicFlow.collect { isNeedRefresh ->
            if (isNeedRefresh) {
                logD("load Road events")
                emit(api.getRoadEvents())

                context.dataStore.edit {
                    it[NEW_TOPIC] = false
                }
            }
        }
    }
}