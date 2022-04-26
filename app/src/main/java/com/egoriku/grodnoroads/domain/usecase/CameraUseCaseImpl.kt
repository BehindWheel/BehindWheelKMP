package com.egoriku.grodnoroads.domain.usecase

import com.egoriku.grodnoroads.data.response.LatLngResponse
import com.egoriku.grodnoroads.data.response.ReportActionResponse
import com.egoriku.grodnoroads.domain.model.Camera
import com.egoriku.grodnoroads.domain.model.CameraType
import com.egoriku.grodnoroads.domain.model.UserAction
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import com.egoriku.grodnoroads.domain.repository.StationaryCameraRepository
import com.google.android.gms.maps.model.LatLng

internal class CameraUseCaseImpl(
    private val stationaryCameraRepository: StationaryCameraRepository,
    private val reportActionRepository: ReportActionRepository
) : CameraUseCase {

    override suspend fun loadStationary() = stationaryCameraRepository.load().map {
        Camera(
            type = CameraType.Stationary,
            message = it.message,
            speed = it.speed,
            position = LatLng(it.position.latitude, it.position.longitude)
        )
    }

    override suspend fun reportAction(type: UserAction, latLng: LatLng) {
        reportActionRepository.report(
            reportActionResponse = ReportActionResponse(
                timestamp = System.currentTimeMillis(),
                type = type.type,
                message = when (type) {
                    UserAction.Police -> "\uD83D\uDC6E"
                    UserAction.Accident -> "\uD83D\uDCA5"
                },
                position = LatLngResponse(latitude = latLng.latitude, longitude = latLng.longitude)
            )
        )
    }
}

interface CameraUseCase {
    suspend fun loadStationary(): List<Camera>

    suspend fun reportAction(type: UserAction, latLng: LatLng)
}

