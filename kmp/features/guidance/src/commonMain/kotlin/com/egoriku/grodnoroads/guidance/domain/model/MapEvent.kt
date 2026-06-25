package com.egoriku.grodnoroads.guidance.domain.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.shared.models.MapEventType

@Stable
sealed interface MapEvent {
    val position: LatLng

    sealed interface Camera : MapEvent {
        val cameraType: CameraType
        val id: String
        val name: String
        val formattedUpdateTime: String
        val angle: Float
        val bidirectional: Boolean
        val speedCar: Int
        val speedTruck: Int

        data class StationaryCamera(
            override val cameraType: CameraType = CameraType.StationaryCamera,
            override val id: String,
            override val name: String,
            override val formattedUpdateTime: String,
            override val angle: Float,
            override val bidirectional: Boolean,
            override val position: LatLng,
            override val speedCar: Int,
            override val speedTruck: Int
        ) : Camera

        data class MobileCamera(
            override val cameraType: CameraType = CameraType.MobileCamera,
            override val id: String,
            override val name: String,
            override val formattedUpdateTime: String,
            override val angle: Float,
            override val bidirectional: Boolean,
            override val position: LatLng,
            override val speedCar: Int,
            override val speedTruck: Int
        ) : Camera

        data class MediumSpeedCamera(
            override val cameraType: CameraType = CameraType.MediumSpeedCamera,
            override val id: String,
            override val name: String,
            override val formattedUpdateTime: String,
            override val angle: Float,
            override val bidirectional: Boolean,
            override val position: LatLng,
            override val speedCar: Int,
            override val speedTruck: Int
        ) : Camera
    }

    data class Reports(
        val id: String,
        val timestamp: Long,
        val markerMessage: String,
        val dialogTitle: String,
        val shortMessage: String,
        val messages: List<MessageItem>,
        val mapEventType: MapEventType,
        override val position: LatLng
    ) : MapEvent
}
