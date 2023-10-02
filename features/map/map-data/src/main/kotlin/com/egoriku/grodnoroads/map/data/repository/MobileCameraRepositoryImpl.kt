package com.egoriku.grodnoroads.map.data.repository

import com.egoriku.grodnoroads.extensions.awaitValueEventListener
import com.egoriku.grodnoroads.extensions.common.ResultOf.Failure
import com.egoriku.grodnoroads.extensions.common.ResultOf.Success
import com.egoriku.grodnoroads.map.data.dto.MobileCameraDTO
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.MobileCamera
import com.egoriku.grodnoroads.map.domain.repository.MobileCameraRepository
import com.egoriku.grodnoroads.maps.core.StableLatLng
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.util.UUID

internal class MobileCameraRepositoryImpl(
    private val databaseReference: DatabaseReference
) : MobileCameraRepository {

    override fun loadAsFlow() = databaseReference
        .child("temporary_camera/cameras")
        .awaitValueEventListener<MobileCameraDTO>()
        .map { resultOf ->
            when (resultOf) {
                is Failure -> Failure(resultOf.exception)
                is Success -> Success(resultOf.value.map { data ->
                    MobileCamera(
                        // TODO: use from backend
                        id = UUID.randomUUID().toString(),
                        name = data.name,
                        position = StableLatLng(data.latitude, data.longitude),
                        speedCar = data.speed,
                        speedTruck = data.speed,
                    )
                })
            }
        }
        .flowOn(Dispatchers.IO)
}