package com.egoriku.grodnoroads.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.egoriku.grodnoroads.data.api.SHEET_ROAD_EVENTS
import com.egoriku.grodnoroads.data.model.ActionResponse
import com.egoriku.grodnoroads.domain.repository.ActionRepository
import com.egoriku.grodnoroads.preferences.NEW_TOPIC
import com.egoriku.grodnoroads.preferences.dataStore
import com.egoriku.grodnoroads.util.encodeMessage
import com.egoriku.retrosheetkmm.ktor.sheet
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.parametersOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

private const val FORM_URL =
    "https://docs.google.com/forms/d/e/1FAIpQLScvuOIcHuVCdR5fZNbwLPFslheGX6olZkr8dpo1gcYmakkIlQ/formResponse"

private const val KEY_TYPE = "entry.1603803799"
private const val KEY_MESSAGE = "entry.352950930"
private const val KEY_LATITUDE = "entry.1994232287"
private const val KEY_LONGITUDE = "entry.516605118"
private const val KEY_ADDED_TIME = "entry.1348191883"

internal class ActionRepositoryImpl(
    private val context: Context,
    private val httpClient: HttpClient
) : ActionRepository {

    private val topicFlow = context.dataStore.data.map { it[NEW_TOPIC] ?: false }

    override suspend fun report(actionResponse: ActionResponse) {
        runCatching {
            httpClient.submitForm(
                url = FORM_URL,
                formParameters = parametersOf(
                    KEY_TYPE to listOf(actionResponse.type),
                    KEY_MESSAGE to listOf(actionResponse.message),
                    KEY_LATITUDE to listOf(actionResponse.latitude.toString()),
                    KEY_LONGITUDE to listOf(actionResponse.longitude.toString()),
                    KEY_ADDED_TIME to listOf(actionResponse.addedTime.toString()),
                )
            )
        }.onFailure {
            Firebase.crashlytics.recordException(it)
        }.getOrNull()
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