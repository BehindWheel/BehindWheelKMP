package com.egoriku.grodnoroads.settings.map.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapDialogState.None
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.CarCrash
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.MapZoomInCity
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.MapZoomOutCity
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.MarkerFiltering
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.MediumSpeedCameras
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.MobileCameras
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.RoadIncident
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.StationaryCameras
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.TrafficJam
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.TrafficJamOnMap
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.TrafficPolice
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.WildAnimals
import com.egoriku.grodnoroads.shared.persistent.Selectable
import com.egoriku.grodnoroads.shared.persistent.map.drivemode.DEFAULT_MAP_ZOOM_IN_CITY
import com.egoriku.grodnoroads.shared.persistent.map.drivemode.DEFAULT_MAP_ZOOM_OUT_CITY
import com.egoriku.grodnoroads.shared.persistent.map.filtering.Filtering
import com.egoriku.grodnoroads.shared.persistent.map.location.City
import com.egoriku.grodnoroads.shared.persistent.map.location.City.Grodno
import kotlinx.coroutines.flow.Flow

@Stable
interface MapSettingsComponent {

    val state: Flow<MapSettingState>

    fun modify(preference: MapPref)
    fun reset(preference: MapPref)
    fun openDialog(preference: MapPref)
    fun closeDialog()

    @Stable
    data class MapSettingState(
        val isLoading: Boolean = true,
        val mapSettings: MapSettings = MapSettings(),
        val mapDialogState: MapDialogState = None
    )

    @Stable
    sealed interface MapDialogState {
        data class DefaultLocationDialogState(val defaultCity: DefaultCity) : MapDialogState
        data object None : MapDialogState
    }

    @Stable
    sealed interface MapPref {
        data class StationaryCameras(val isShow: Boolean = true) : MapPref
        data class MediumSpeedCameras(val isShow: Boolean = true) : MapPref
        data class MobileCameras(val isShow: Boolean = true) : MapPref
        data class TrafficPolice(val isShow: Boolean = true) : MapPref
        data class RoadIncident(val isShow: Boolean = true) : MapPref
        data class TrafficJam(val isShow: Boolean = true) : MapPref
        data class WildAnimals(val isShow: Boolean = true) : MapPref
        data class CarCrash(val isShow: Boolean = true) : MapPref
        data class SelectAll(val selectAll: Boolean = true) : MapPref

        data class TrafficJamOnMap(val isShow: Boolean = false) : MapPref

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
            val values: List<City> = City.entries
        ) : MapPref

        data class MarkerFiltering(
            val current: Filtering = Filtering.Hours1,
            val values: List<Filtering> = Filtering.entries
        ) : MapPref
    }

    @Stable
    data class MapSettings(
        val locationInfo: LocationInfo = LocationInfo(),
        val driveModeZoom: DriveModeZoom = DriveModeZoom(),
        val mapStyle: MapStyle = MapStyle(),
        val markerFiltering: MarkerFiltering = MarkerFiltering(),
        val mapInfo: MapInfo = MapInfo()
    ) {
        @Stable
        data class LocationInfo(
            val defaultCity: DefaultCity = DefaultCity()
        )

        @Stable
        data class DriveModeZoom(
            val mapZoomInCity: MapZoomInCity = MapZoomInCity(),
            val mapZoomOutCity: MapZoomOutCity = MapZoomOutCity()
        )

        @Stable
        data class MapInfo(
            val stationaryCameras: StationaryCameras = StationaryCameras(),
            val mediumSpeedCameras: MediumSpeedCameras = MediumSpeedCameras(),
            val mobileCameras: MobileCameras = MobileCameras(),
            val trafficPolice: TrafficPolice = TrafficPolice(),
            val roadIncident: RoadIncident = RoadIncident(),
            val carCrash: CarCrash = CarCrash(),
            val trafficJam: TrafficJam = TrafficJam(),
            val wildAnimals: WildAnimals = WildAnimals(),
            val selectable: Selectable = Selectable.AllEnabled
        )

        @Stable
        data class MapStyle(val trafficJamOnMap: TrafficJamOnMap = TrafficJamOnMap())
    }
}
