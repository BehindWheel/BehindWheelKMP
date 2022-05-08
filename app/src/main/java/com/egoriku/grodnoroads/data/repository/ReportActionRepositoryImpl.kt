package com.egoriku.grodnoroads.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.egoriku.grodnoroads.data.api.GrodnoRoadsApi
import com.egoriku.grodnoroads.data.api.SHEET_ROAD_EVENTS
import com.egoriku.grodnoroads.data.model.ActionResponse
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import com.egoriku.grodnoroads.preferences.NEW_TOPIC
import com.egoriku.grodnoroads.preferences.dataStore
import com.egoriku.retrosheetkmm.ktor.sheet
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class ReportActionRepositoryImpl(
    private val context: Context,
    private val api: GrodnoRoadsApi,
    private val httpClient: HttpClient
) : ReportActionRepository {

    private val topicFlow = context.dataStore.data
        .map { it[NEW_TOPIC] ?: false }

    override suspend fun report(actionResponse: ActionResponse) {
        api.sendReport(actionResponse)
    }

    override fun usersActions(): Flow<List<ActionResponse>> = flow {
        emit(loadEvents())

        topicFlow.collect { isNeedRefresh ->
            if (isNeedRefresh) {
                emit(loadEvents())

                context.dataStore.edit {
                    it[NEW_TOPIC] = false
                }
            }
        }
    }

    private suspend fun loadEvents() = runCatching {
        httpClient.get {
            sheet(SHEET_ROAD_EVENTS)
        }.body<List<ActionResponse>>()
    }.onFailure {
        Firebase.crashlytics.recordException(it)
    }.getOrDefault(emptyList())
}