package com.egoriku.grodnoroads.map.data.repository

import com.egoriku.grodnoroads.extensions.awaitValueEventListener
import com.egoriku.grodnoroads.extensions.common.ResultOf.Failure
import com.egoriku.grodnoroads.extensions.common.ResultOf.Success
import com.egoriku.grodnoroads.shared.core.models.dto.MobileCameraDTO
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.MobileCamera
import com.egoriku.grodnoroads.map.domain.repository.MobileCameraRepository
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class MobileCameraRepositoryImpl(
    private val databaseReference: DatabaseReference
) : MobileCameraRepository {

    private val currentTime = System.currentTimeMillis()

    override fun loadAsFlow() = databaseReference
        .child("/v2/mobile_cameras/cameras")
        .awaitValueEventListener<MobileCameraDTO>()
        .map { resultOf ->
            when (resultOf) {
                is Failure -> Failure(resultOf.exception)
                is Success -> Success(resultOf.value.map { data ->
                    MobileCamera(
                        id = data.id,
                        name = data.name,
                        position = LatLng(data.latitude, data.longitude),
                        updateTime = currentTime,
                        speedCar = data.speed,
                        speedTruck = data.speed,
                        angle = data.angle,
                        bidirectional = data.bidirectional
                    )
                })
            }
        }
        .flowOn(Dispatchers.IO)
}