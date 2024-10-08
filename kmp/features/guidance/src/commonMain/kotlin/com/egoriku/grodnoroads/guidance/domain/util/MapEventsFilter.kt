package com.egoriku.grodnoroads.guidance.domain.util

import com.egoriku.grodnoroads.guidance.domain.model.CameraType
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.MediumSpeedCamera
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.MobileCamera
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.StationaryCamera
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.guidance.domain.model.MapEvents
import com.egoriku.grodnoroads.guidance.domain.model.MapInternalConfig.MapInfo
import com.egoriku.grodnoroads.shared.models.MapEventType
import kotlinx.collections.immutable.toImmutableList

internal fun filterMapEvents(): suspend (
    List<Reports>,
    List<StationaryCamera>,
    List<MobileCamera>,
    List<MediumSpeedCamera>,
    MapInfo
) -> MapEvents = { reports, stationary, mobile, mediumSpeed, mapInfo ->
    MapEvents(
        data = mapEvents(
            events = reports + stationary + mediumSpeed + mobile,
            mapInfo = mapInfo
        ).toImmutableList()
    )
}

private fun mapEvents(
    events: List<MapEvent>,
    mapInfo: MapInfo
) = events.mapNotNull { event ->
    when (event) {
        is Camera -> {
            when (event.cameraType) {
                CameraType.MobileCamera -> when {
                    mapInfo.showMobileCameras -> event
                    else -> null
                }
                CameraType.StationaryCamera -> when {
                    mapInfo.showStationaryCameras -> event
                    else -> null
                }
                CameraType.MediumSpeedCamera -> when {
                    mapInfo.showMediumSpeedCameras -> event
                    else -> null
                }
            }
        }
        is Reports -> {
            when (event.mapEventType) {
                MapEventType.RoadIncident -> {
                    when {
                        mapInfo.showRoadIncident -> event
                        else -> null
                    }
                }
                MapEventType.TrafficPolice -> {
                    when {
                        mapInfo.showTrafficPolice -> event
                        else -> null
                    }
                }
                MapEventType.CarCrash -> {
                    when {
                        mapInfo.showCarCrash -> event
                        else -> null
                    }
                }
                MapEventType.TrafficJam -> {
                    when {
                        mapInfo.showTrafficJam -> event
                        else -> null
                    }
                }
                MapEventType.WildAnimals -> {
                    when {
                        mapInfo.showWildAnimals -> event
                        else -> null
                    }
                }
                else -> event
            }
        }
    }
}
