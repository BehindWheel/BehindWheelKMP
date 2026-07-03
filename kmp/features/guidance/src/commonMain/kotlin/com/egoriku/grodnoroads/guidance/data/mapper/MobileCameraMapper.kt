package com.egoriku.grodnoroads.guidance.data.mapper

import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.MobileCamera
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.calc.roundDistanceTo
import com.egoriku.grodnoroads.shared.models.dto.MobileCameraDTO

private const val MERGE_CAMERA_DISTANCE = 300

internal object MobileCameraMapper : (List<MobileCameraDTO>, String) -> List<MobileCamera> {

    override fun invoke(
        camerasDTO: List<MobileCameraDTO>,
        formattedUpdateTime: String
    ): List<MobileCamera> {
        val mergedCameras = mutableListOf<MobileCamera>()

        camerasDTO.forEach { data ->
            val position = LatLng(data.latitude, data.longitude)

            val index = mergedCameras.indexOfFirst { camera ->
                camera.position roundDistanceTo position < MERGE_CAMERA_DISTANCE
            }

            if (index != -1) {
                val item = mergedCameras[index]

                // prioritize the latest camera's id/name over the merged group
                mergedCameras[index] = item.copy(
                    id = data.id,
                    name = data.name,
                    position = position,
                    speedCar = data.speed,
                    speedTruck = data.speed,
                    angle = data.angle,
                    bidirectional = data.bidirectional
                )
            } else {
                mergedCameras += MobileCamera(
                    id = data.id,
                    name = data.name,
                    position = position,
                    speedCar = data.speed,
                    speedTruck = data.speed,
                    formattedUpdateTime = formattedUpdateTime,
                    angle = data.angle,
                    bidirectional = data.bidirectional
                )
            }
        }
        return mergedCameras
    }
}
