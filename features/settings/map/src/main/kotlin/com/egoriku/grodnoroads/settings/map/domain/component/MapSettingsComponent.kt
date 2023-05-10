package com.egoriku.grodnoroads.settings.map.domain.component

import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapDialogState.None
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.*
import com.egoriku.grodnoroads.shared.appsettings.types.map.drivemode.DEFAULT_MAP_ZOOM_IN_CITY
import com.egoriku.grodnoroads.shared.appsettings.types.map.drivemode.DEFAULT_MAP_ZOOM_OUT_CITY
import com.egoriku.grodnoroads.shared.appsettings.types.map.location.City
import com.egoriku.grodnoroads.shared.appsettings.types.map.location.City.Companion.supportedCities
import com.egoriku.grodnoroads.shared.appsettings.types.map.location.City.Grodno
import com.egoriku.grodnoroads.shared.appsettings.types.map.mapstyle.Style
import kotlinx.coroutines.flow.Flow

interface MapSettingsComponent {

    val mapSettingsState: Flow<MapSettingState>

    fun modify(preference: MapPref)
    fun reset(preference: MapPref)
    fun openDialog(preference: MapPref)
    fun closeDialog()

    data class MapSettingState(
        val isLoading: Boolean = true,
        val mapSettings: MapSettings = MapSettings(),
        val mapDialogState: MapDialogState = None
    )

    sealed interface MapDialogState {
        data class DefaultLocationDialogState(val defaultCity: DefaultCity) : MapDialogState
        object None : MapDialogState
    }

    sealed interface MapPref {
        data class StationaryCameras(val isShow: Boolean = true) : MapPref
        data class MediumSpeedCameras(val isShow: Boolean = true) : MapPref
        data class MobileCameras(val isShow: Boolean = true) : MapPref
        data class TrafficPolice(val isShow: Boolean = true) : MapPref
        data class RoadIncident(val isShow: Boolean = true) : MapPref
        data class TrafficJam(val isShow: Boolean = true) : MapPref
        data class WildAnimals(val isShow: Boolean = true) : MapPref
        data class CarCrash(val isShow: Boolean = true) : MapPref

        data class TrafficJamOnMap(val isShow: Boolean = false) : MapPref
        data class GoogleMapStyle(val style: Style = Style.Minimal) : MapPref

        data class MapZoomInCity(
            val current: Float = DEFAULT_MAP_ZOOM_IN_CITY,
            val min: Float = 13f,
            val max: Float = 16.5f,
            val stepSize: Float = 0.1f
        ) : MapPref

        data class MapZoomOutCity(
            val current: Float = DEFAULT_MAP_ZOOM_OUT_CITY,
            val min: Float = 12f,
            val max: Float = 15.0f,
            val stepSize: Float = 0.1f
        ) : MapPref

        data class DefaultCity(
            val current: City = Grodno,
            val values: List<City> = supportedCities
        ) : MapPref
    }

    data class MapSettings(
        val locationInfo: LocationInfo = LocationInfo(),
        val driveModeZoom: DriveModeZoom = DriveModeZoom(),
        val mapStyle: MapStyle = MapStyle(),
        val mapInfo: MapInfo = MapInfo(),
    ) {
        data class LocationInfo(
            val defaultCity: DefaultCity = DefaultCity()
        )

        data class DriveModeZoom(
            val mapZoomInCity: MapZoomInCity = MapZoomInCity(),
            val mapZoomOutCity: MapZoomOutCity = MapZoomOutCity(),
        )

        data class MapInfo(
            val stationaryCameras: StationaryCameras = StationaryCameras(),
            val mediumSpeedCameras: MediumSpeedCameras = MediumSpeedCameras(),
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