package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.extensions.DateTime
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.extensions.common.ResultOf.Success
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.MobileCamera
import com.egoriku.grodnoroads.guidance.domain.repository.MobileCameraRepository
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.shared.models.dto.MobileCameraDTO
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class MobileCameraRepositoryImpl(
    private val databaseReference: DatabaseReference
) : MobileCameraRepository {

    private val currentTime = DateTime.currentTimeMillis()

    override fun loadAsFlow() = databaseReference
        .child("/v2/mobile_cameras/cameras")
        .valueEvents
        .map { resultOf ->
            Success(resultOf.value<List<MobileCameraDTO>>().map { data ->
                MobileCamera(
                    id = data.id,
                    name = data.name,
                    position = LatLng(data.latitude, data.longitude),
                    speedCar = data.speed,
                    speedTruck = data.speed,
                    updateTime = currentTime,
                    angle = data.angle,
                    bidirectional = data.bidirectional
                )
            })
        }
        .catch { ResultOf.Failure(it) }
        .flowOn(Dispatchers.IO)
}