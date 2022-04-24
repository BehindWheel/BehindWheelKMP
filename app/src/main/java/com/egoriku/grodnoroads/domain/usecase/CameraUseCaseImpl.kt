package com.egoriku.grodnoroads.domain.usecase

import com.egoriku.grodnoroads.domain.model.Camera
import com.egoriku.grodnoroads.domain.model.CameraType
import com.egoriku.grodnoroads.domain.repository.StationaryCameraRepository
import com.google.android.gms.maps.model.LatLng

internal class CameraUseCaseImpl(
    private val stationaryCameraRepository: StationaryCameraRepository
) : CameraUseCase {

    override suspend fun loadStationary() = stationaryCameraRepository.load().map {
        Camera(
            type = CameraType.Stationary,
            message = it.message,
            speed = it.speed,
            position = LatLng(it.position.latitude, it.position.longitude)
        )
    }
}

interface CameraUseCase {
    suspend fun loadStationary(): List<Camera>
}

