package com.egoriku.grodnoroads.map.data.repository

import com.egoriku.grodnoroads.extensions.awaitSingleValueEventListener
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.extensions.common.ResultOf.Failure
import com.egoriku.grodnoroads.extensions.common.ResultOf.Success
import com.egoriku.grodnoroads.map.data.dto.MediumSpeedDTO
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.MediumSpeedCamera
import com.egoriku.grodnoroads.map.domain.repository.MediumSpeedCameraRepository
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class MediumSpeedCameraRepositoryImpl(
    private val databaseReference: DatabaseReference
) : MediumSpeedCameraRepository {

    override fun loadAsFlow(): Flow<ResultOf<List<MediumSpeedCamera>>> {
        return databaseReference
            .child("v2/medium_speed_cameras")
            .awaitSingleValueEventListener<MediumSpeedDTO>()
            .map { resultOf ->
                when (resultOf) {
                    is Failure -> Failure(resultOf.throwable)
                    is Success -> Success(resultOf.value.map { data ->
                        MediumSpeedCamera(
                            id = data.id,
                            name = data.name,
                            angle = data.angle,
                            bidirectional = data.bidirectional,
                            updateTime = data.updateTime,
                            speedCar = data.speedCar,
                            speedTruck = data.speedTruck,
                            position = LatLng(data.latitude, data.longitude)
                        )
                    })
                }
            }
            .flowOn(Dispatchers.IO)
    }
}