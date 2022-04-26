package com.egoriku.grodnoroads.domain.usecase

import com.egoriku.grodnoroads.data.response.LatLngResponse
import com.egoriku.grodnoroads.data.response.ReportActionResponse
import com.egoriku.grodnoroads.domain.model.Camera
import com.egoriku.grodnoroads.domain.model.MapEvent
import com.egoriku.grodnoroads.domain.model.UserActionType
import com.egoriku.grodnoroads.domain.model.UserActionType.Companion.valueOf
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import com.egoriku.grodnoroads.domain.repository.StationaryCameraRepository
import com.egoriku.grodnoroads.util.DateUtil
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CameraUseCaseImpl(
    private val stationaryCameraRepository: StationaryCameraRepository,
    private val reportActionRepository: ReportActionRepository
) : CameraUseCase {

    override suspend fun loadStationary() = stationaryCameraRepository.load().map {
        Camera(
            message = it.message,
            speed = it.speed,
            position = LatLng(it.position.latitude, it.position.longitude)
        )
    }

    override suspend fun reportAction(type: UserActionType, latLng: LatLng) {
        reportActionRepository.report(
            reportActionResponse = ReportActionResponse(
                timestamp = System.currentTimeMillis(),
                type = type.type,
                message = when (type) {
                    UserActionType.Police -> "\uD83D\uDC6E"
                    UserActionType.Accident -> "\uD83D\uDCA5"
                },
                position = LatLngResponse(latitude = latLng.latitude, longitude = latLng.longitude)
            )
        )
    }

    override fun usersActions(): Flow<List<MapEvent>> {
        return reportActionRepository.usersActions().map {
            it.map { response ->
                MapEvent(
                    type = valueOf(response.type),
                    message = response.message,
                    position = LatLng(response.position.latitude, response.position.longitude),
                    time = DateUtil.formatToTime(date = response.timestamp)
                )
            }
        }
    }
}

interface CameraUseCase {
    suspend fun loadStationary(): List<Camera>

    fun usersActions(): Flow<List<MapEvent>>

    suspend fun reportAction(type: UserActionType, latLng: LatLng)
}

