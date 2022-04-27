package com.egoriku.grodnoroads.domain.usecase

import com.egoriku.grodnoroads.data.model.ActionResponse
import com.egoriku.grodnoroads.domain.model.Camera
import com.egoriku.grodnoroads.domain.model.MapEvent
import com.egoriku.grodnoroads.domain.model.UserActionType
import com.egoriku.grodnoroads.domain.model.UserActionType.Companion.valueOf
import com.egoriku.grodnoroads.domain.repository.ReportActionRepository
import com.egoriku.grodnoroads.domain.repository.StationaryCameraRepository
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
        Camera(
            message = it.message,
            speed = it.speed,
            position = LatLng(it.latitude, it.longitude)
        )
    }

    override suspend fun reportAction(type: UserActionType, latLng: LatLng) {
        reportActionRepository.report(
            actionResponse = ActionResponse(
                addedTime = System.currentTimeMillis(),
                type = type.type,
                message = when (type) {
                    UserActionType.Police -> "\uD83D\uDC6E"
                    UserActionType.Accident -> "\uD83D\uDCA5"
                }.encodeMessage(),
                latitude = latLng.latitude,
                longitude = latLng.longitude
            )
        )
    }

    override fun usersActions(): Flow<List<MapEvent>> {
        return reportActionRepository.usersActions().map {
            it.map { response ->
                MapEvent(
                    type = valueOf(response.type),
                    message = response.message,
                    position = LatLng(response.latitude, response.longitude),
                    time = DateUtil.formatToTime(date = response.addedTime)
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

