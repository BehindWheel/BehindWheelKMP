package com.egoriku.grodnoroads.map.domain.util

import com.egoriku.grodnoroads.map.domain.model.MapEvent
import com.egoriku.grodnoroads.map.domain.model.MapEvent.*
import com.egoriku.grodnoroads.map.domain.model.MapEventType
import com.egoriku.grodnoroads.map.domain.model.MapInternalConfig.MapInfo
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal fun filterMapEvents(): suspend (
    List<Reports>,
    List<StationaryCamera>,
    List<MobileCamera>,
    MapInfo
) -> ImmutableList<MapEvent> = { reports, stationary, mobile, mapInfo ->
    (reports + stationary + mobile).mapNotNull { mapEvent ->

        when (mapEvent) {
            is MobileCamera -> {
                when {
                    mapInfo.showMobileCameras -> mapEvent
                    else -> null
                }
            }
            is StationaryCamera -> {
                when {
                    mapInfo.showStationaryCameras -> mapEvent
                    else -> null
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
