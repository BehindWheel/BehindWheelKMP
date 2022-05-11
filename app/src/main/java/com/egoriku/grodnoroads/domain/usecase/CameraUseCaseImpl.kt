package com.egoriku.grodnoroads.domain.usecase

import com.egoriku.grodnoroads.data.model.ActionResponse
import com.egoriku.grodnoroads.domain.model.EventType
import com.egoriku.grodnoroads.domain.model.EventType.Companion.valueOf
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import com.egoriku.grodnoroads.domain.repository.StationaryCameraRepository
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent.StationaryCamera
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent.UserActions
import com.egoriku.grodnoroads.util.DateUtil
import com.egoriku.grodnoroads.util.encodeMessage
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CameraUseCaseImpl(
    private val stationaryCameraRepository: StationaryCameraRepository,
    private val reportActionRepository: ReportActionRepository
) : CameraUseCase {

    override suspend fun loadStationary() = stationaryCameraRepository.load().map {
        StationaryCamera(
            message = it.message,
            speed = it.speed,
            position = LatLng(it.latitude, it.longitude)
        )
    }

    override suspend fun reportAction(type: EventType, latLng: LatLng) {
        reportActionRepository.report(
            actionResponse = ActionResponse(
                addedTime = System.currentTimeMillis(),
                type = type.type,
                message = when (type) {
                    EventType.Police -> "\uD83D\uDC6E"
                    EventType.Accident -> "\uD83D\uDCA5"
                    else -> throw IllegalArgumentException("reporting $type is not supporting")
                }.encodeMessage(),
                latitude = latLng.latitude,
                longitude = latLng.longitude
            )
        )
    }

    override fun usersActions(): Flow<List<UserActions>> {
        return reportActionRepository.usersActions().map {
            it.map { response ->
                UserActions(
                    eventType = valueOf(response.type),
                    message = response.message,
                    position = LatLng(response.latitude, response.longitude),
                    time = DateUtil.formatToTime(date = response.addedTime)
                )
            }
        }
    }
}

interface CameraUseCase {
    suspend fun loadStationary(): List<StationaryCamera>

    fun usersActions(): Flow<List<UserActions>>

    suspend fun reportAction(type: EventType, latLng: LatLng)
}

