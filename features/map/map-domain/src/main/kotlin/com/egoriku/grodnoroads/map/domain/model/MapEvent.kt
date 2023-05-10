package com.egoriku.grodnoroads.map.domain.model

import androidx.compose.runtime.Stable
import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.ImmutableList

@Stable
sealed interface MapEvent {
    val position: LatLng

    sealed interface Camera : MapEvent {
        val cameraType: CameraType

        data class StationaryCamera(
            val id: Int,
            val name: String,
            val angle: Float,
            val bidirectional: Boolean,
            val updateTime: Long,
            val speedCar: Int,
            val speedTruck: Int,
            override val position: LatLng,
            override val cameraType: CameraType = CameraType.StationaryCamera
        ) : Camera

        data class MobileCamera(
            val message: String,
            val speed: Int,
            override val position: LatLng,
            override val cameraType: CameraType = CameraType.MobileCamera,
        ) : Camera

        data class MediumSpeedCamera(
            val id: Int,
            val name: String,
            val angle: Float,
            val bidirectional: Boolean,
            val updateTime: Long,
            val speedCar: Int,
            val speedTruck: Int,
            override val position: LatLng,
            override val cameraType: CameraType = CameraType.MediumSpeedCamera
        ) : Camera
    }

    data class Reports(
        val markerMessage: String,
        val dialogTitle: String,
        val messages: ImmutableList<MessageItem>,
        val mapEventType: MapEventType,
        override val position: LatLng,
    ) : MapEvent
}