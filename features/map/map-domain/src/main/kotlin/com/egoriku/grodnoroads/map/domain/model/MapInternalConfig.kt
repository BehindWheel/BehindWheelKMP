package com.egoriku.grodnoroads.map.domain.model

import com.egoriku.grodnoroads.shared.appsettings.types.map.mapstyle.Style

internal data class MapInternalConfig(
    val zoomLevelInCity: Float,
    val zoomLevelOutOfCity: Float,
    val alertDistance: Int,
    val mapInfo: MapInfo,
    val googleMapStyle: Style,
    val trafficJanOnMap: Boolean,
    val keepScreenOn: Boolean
) {
    internal data class MapInfo(
        val showStationaryCameras: Boolean,
        val showMediumSpeedCameras: Boolean,
        val showMobileCameras: Boolean,
        val showRoadIncident: Boolean,
        val showTrafficPolice: Boolean,
        val showCarCrash: Boolean,
        val showTrafficJam: Boolean,
        val showWildAnimals: Boolean,
    )

    companion object {
        val EMPTY = MapInternalConfig(
            zoomLevelInCity = -1f,
            zoomLevelOutOfCity = -1f,
            alertDistance = -1,
            mapInfo = MapInfo(
                showStationaryCameras = false,
                showMediumSpeedCameras = false,
                showMobileCameras = false,
                showRoadIncident = false,
                showTrafficPolice = false,
                showCarCrash = false,
                showTrafficJam = false,
                showWildAnimals = false
            ),
            googleMapStyle = Style.Minimal,
            trafficJanOnMap = false,
            keepScreenOn = false
        )
    }
}
