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
        when (mapEvent) {
            is MobileCamera -> {
                when {
                    settings.mobileCameras.isShow -> mapEvent
                    else -> null
                }
            }
            is StationaryCamera -> {
                when {
                    settings.stationaryCameras.isShow -> mapEvent
                    else -> null
                }
            }
            is Reports -> {
                when (mapEvent.mapEventType) {
                    MapEventType.RoadAccident -> {
                        when {
                            settings.incidents.isShow -> mapEvent
                            else -> null
                        }
                    }
                    MapEventType.TrafficPolice -> {
                        when {
                            settings.trafficPolice.isShow -> mapEvent
                            else -> null
                        }
                    }
                    else -> mapEvent
                }
            }
        }
    }
}
