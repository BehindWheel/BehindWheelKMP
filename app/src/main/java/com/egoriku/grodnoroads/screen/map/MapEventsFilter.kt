package com.egoriku.grodnoroads.screen.map

import com.egoriku.grodnoroads.screen.map.domain.MapEvent
import com.egoriku.grodnoroads.screen.map.domain.MapEvent.*
import com.egoriku.grodnoroads.screen.map.domain.MapEventType
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.SettingsState

fun filterMapEvents(): suspend (
    List<Reports>,
    List<StationaryCamera>,
    List<MobileCamera>,
    SettingsState
) -> List<MapEvent> = { reports, stationary, mobile, settings ->
    (reports + stationary + mobile).mapNotNull { mapEvent ->
        val mapInfo = settings.mapInfo

        when (mapEvent) {
            is MobileCamera -> {
                when {
                    mapInfo.mobileCameras.isShow -> mapEvent
                    else -> null
                }
            }
            is StationaryCamera -> {
                when {
                    mapInfo.stationaryCameras.isShow -> mapEvent
                    else -> null
                }
            }
            is Reports -> {
                when (mapEvent.mapEventType) {
                    MapEventType.RoadIncident -> {
                        when {
                            mapInfo.roadIncident.isShow -> mapEvent
                            else -> null
                        }
                    }
                    MapEventType.TrafficPolice -> {
                        when {
                            mapInfo.trafficPolice.isShow -> mapEvent
                            else -> null
                        }
                    }
                    MapEventType.CarCrash -> {
                        when {
                            mapInfo.carCrash.isShow -> mapEvent
                            else -> null
                        }
                    }
                    MapEventType.TrafficJam -> {
                        when {
                            mapInfo.trafficJam.isShow -> mapEvent
                            else -> null
                        }
                    }
                    MapEventType.WildAnimals -> {
                        when {
                            mapInfo.wildAnimals.isShow -> mapEvent
                            else -> null
                        }
                    }
                    else -> mapEvent
                }
            }
        }
    }
}
