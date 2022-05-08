package com.egoriku.grodnoroads.data.repository

import com.egoriku.grodnoroads.data.api.SHEET_STATIONARY_CAMERA
import com.egoriku.grodnoroads.data.model.StationaryResponse
import com.egoriku.grodnoroads.domain.repository.StationaryCameraRepository
import com.egoriku.retrosheetkmm.ktor.sheet
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

internal class StationaryCameraRepositoryImpl(
    private val httpClient: HttpClient
) : StationaryCameraRepository {

    override suspend fun load() = runCatching {
        httpClient.get {
            sheet(SHEET_STATIONARY_CAMERA)
        }.body<List<StationaryResponse>>()
    }.onFailure {
        Firebase.crashlytics.recordException(it)
    }.getOrDefault(emptyList())
}