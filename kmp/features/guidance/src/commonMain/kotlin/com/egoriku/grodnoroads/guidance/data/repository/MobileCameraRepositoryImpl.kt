package com.egoriku.grodnoroads.guidance.data.repository

import com.egoriku.grodnoroads.extensions.DateTime
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.MobileCamera
import com.egoriku.grodnoroads.guidance.domain.repository.MobileCameraRepository
import com.egoriku.grodnoroads.location.LatLng
import dev.gitlive.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.flowOf

internal class MobileCameraRepositoryImpl(
    private val databaseReference: DatabaseReference
) : MobileCameraRepository {

    private val currentTime = DateTime.currentTimeMillis()

    override fun loadAsFlow() = flowOf(
        ResultOf.Success(
            listOf(
                MobileCamera(
                    id = "1",
                    name = "Гродно, ул. Горновых",
                    position = LatLng(53.670828, 23.824659),
                    speedCar = 60,
                    speedTruck = 60,
                    updateTime = currentTime,
                    angle = 216f,
                    bidirectional = true
                ),
                MobileCamera(
                    id = "2",
                    name = "Гродно, ул. Поповича",
                    position = LatLng(53.676759, 23.805125),
                    speedCar = 60,
                    speedTruck = 60,
                    updateTime = currentTime,
                    angle = 216f,
                    bidirectional = true
                )
            )
        )
    )
}
