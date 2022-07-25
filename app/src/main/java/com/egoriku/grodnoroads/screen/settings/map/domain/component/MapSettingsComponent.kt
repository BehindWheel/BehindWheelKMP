package com.egoriku.grodnoroads.screen.settings.map.domain.component

import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.*
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.State
import kotlinx.coroutines.flow.Flow

interface MapSettingsComponent {

    val state: Flow<State>

    fun onCheckedChanged(preference: MapPref)

    sealed interface MapPref {
        data class StationaryCameras(val isShow: Boolean = true) : MapPref
        data class MobileCameras(val isShow: Boolean = true) : MapPref
        data class TrafficPolice(val isShow: Boolean = true) : MapPref
        data class RoadIncident(val isShow: Boolean = true) : MapPref
        data class TrafficJam(val isShow: Boolean = true) : MapPref
        data class WildAnimals(val isShow: Boolean = true) : MapPref
        data class CarCrash(val isShow: Boolean = true) : MapPref

        data class TrafficJamOnMap(val isShow: Boolean = false) : MapPref
        data class GoogleMapStyle(val style: Style = Style.Minimal) : MapPref {
            enum class Style(val type: String) {
                Minimal(type = "minimalistic"),
                Detailed(type = "detailed")
            }
        }
    }

    data class MapSettingsState(
        val mapInfo: MapInfo = MapInfo(),
        val mapStyle: MapStyle = MapStyle(),
    ) {
        data class MapInfo(
            val stationaryCameras: StationaryCameras = StationaryCameras(),
            val mobileCameras: MobileCameras = MobileCameras(),
            val trafficPolice: TrafficPolice = TrafficPolice(),
            val roadIncident: RoadIncident = RoadIncident(),
            val carCrash: CarCrash = CarCrash(),
            val trafficJam: TrafficJam = TrafficJam(),
            val wildAnimals: WildAnimals = WildAnimals(),
        )

        data class MapStyle(
            val trafficJamOnMap: TrafficJamOnMap = TrafficJamOnMap(),
            val googleMapStyle: GoogleMapStyle = GoogleMapStyle(),
        )
    }
}