package com.egoriku.grodnoroads.map.domain.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.mapswrapper.core.StableLatLng
import kotlinx.collections.immutable.ImmutableList

@Stable
sealed interface MapEvent {
    val position: StableLatLng

    sealed interface Camera : MapEvent {
        val cameraType: CameraType
        val name: String
        val speedCar: Int
        val speedTruck: Int
        val updateTime: Long

        data class StationaryCamera(
            val id: String,
            val angle: Float,
            val bidirectional: Boolean,
            override val cameraType: CameraType = CameraType.StationaryCamera,
            override val name: String,
            override val position: StableLatLng,
            override val speedCar: Int,
            override val speedTruck: Int,
            override val updateTime: Long,
        ) : Camera

        data class MobileCamera(
            val id: String,
            override val cameraType: CameraType = CameraType.MobileCamera,
            override val name: String,
            override val position: StableLatLng,
            override val updateTime: Long = System.currentTimeMillis(),
            // TODO: Implement in backend
            override val speedCar: Int = -1,
            override val speedTruck: Int = -1,
        ) : Camera

        data class MediumSpeedCamera(
            val id: String,
            val angle: Float,
            val bidirectional: Boolean,
            override val cameraType: CameraType = CameraType.MediumSpeedCamera,
            override val name: String,
            override val position: StableLatLng,
            override val speedCar: Int,
            override val speedTruck: Int,
            override val updateTime: Long,
        ) : Camera
    }

    data class Reports(
        val id: String,
        val markerMessage: String,
        val dialogTitle: String,
        val messages: ImmutableList<MessageItem>,
        val mapEventType: MapEventType,
        override val position: StableLatLng,
    ) : MapEvent
}