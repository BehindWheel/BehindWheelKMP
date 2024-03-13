package com.egoriku.grodnoroads.map.domain.util

import com.egoriku.grodnoroads.map.domain.model.CameraType
import com.egoriku.grodnoroads.map.domain.model.MapEvent
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.MobileCamera
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.StationaryCamera
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.shared.core.models.MapEventType
import com.egoriku.grodnoroads.map.domain.model.MapInternalConfig.MapInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal fun filterMapEvents(): suspend (
    List<Reports>,
    List<StationaryCamera>,
    List<MobileCamera>,
    List<Camera.MediumSpeedCamera>,
    MapInfo
) -> ImmutableList<MapEvent> = { reports, stationary, mobile, mediumSpeed, mapInfo ->
    (reports + stationary + mediumSpeed + mobile).mapNotNull { mapEvent ->

        when (mapEvent) {
            is Camera -> {
                when (mapEvent.cameraType) {
                    CameraType.MobileCamera -> when {
                        mapInfo.showMobileCameras -> mapEvent
                        else -> null
                    }

                    CameraType.StationaryCamera -> when {
                        mapInfo.showStationaryCameras -> mapEvent
                        else -> null
                    }

                    CameraType.MediumSpeedCamera -> when {
                        mapInfo.showMediumSpeedCameras -> mapEvent
                        else -> null
                    }
                }
            }

            is Reports -> {
                when (mapEvent.mapEventType) {
                    MapEventType.RoadIncident -> {
                        when {
                            mapInfo.showRoadIncident -> mapEvent
                            else -> null
                        }
                    }

                    MapEventType.TrafficPolice -> {
                        when {
                            mapInfo.showTrafficPolice -> mapEvent
                            else -> null
                        }
                    }

                    MapEventType.CarCrash -> {
                        when {
                            mapInfo.showCarCrash -> mapEvent
                            else -> null
                        }
                    }

                    MapEventType.TrafficJam -> {
                        when {
                            mapInfo.showTrafficJam -> mapEvent
                            else -> null
                        }
                    }

                    MapEventType.WildAnimals -> {
                        when {
                            mapInfo.showWildAnimals -> mapEvent
                            else -> null
                        }
                    }

                    else -> mapEvent
                }
            }
        }
    }.toImmutableList()
}
