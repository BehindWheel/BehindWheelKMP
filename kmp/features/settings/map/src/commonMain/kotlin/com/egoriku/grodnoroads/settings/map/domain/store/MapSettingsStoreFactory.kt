package com.egoriku.grodnoroads.settings.map.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapDialogState.DefaultLocationDialogState
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapDialogState.None
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.CarCrash
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.GoogleMapStyle
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.MapZoomInCity
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.MapZoomOutCity
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.MarkerFiltering
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.MediumSpeedCameras
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.MobileCameras
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.RoadIncident
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.SelectAll
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.StationaryCameras
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.TrafficJam
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.TrafficJamOnMap
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.TrafficPolice
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref.WildAnimals
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.DriveModeZoom
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.LocationInfo
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.MapInfo
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettings.MapStyle
import com.egoriku.grodnoroads.settings.map.domain.store.MapSettingsStore.Intent
import com.egoriku.grodnoroads.settings.map.domain.store.MapSettingsStore.Message
import com.egoriku.grodnoroads.settings.map.domain.store.MapSettingsStore.StoreState
import com.egoriku.grodnoroads.shared.persistent.Selectable
import com.egoriku.grodnoroads.shared.persistent.map.drivemode.DEFAULT_MAP_ZOOM_IN_CITY
import com.egoriku.grodnoroads.shared.persistent.map.drivemode.DEFAULT_MAP_ZOOM_OUT_CITY
import com.egoriku.grodnoroads.shared.persistent.map.drivemode.mapZoomInCity
import com.egoriku.grodnoroads.shared.persistent.map.drivemode.mapZoomOutCity
import com.egoriku.grodnoroads.shared.persistent.map.drivemode.updateMapZoomInCity
import com.egoriku.grodnoroads.shared.persistent.map.drivemode.updateMapZoomOutsideCity
import com.egoriku.grodnoroads.shared.persistent.map.filtering.filteringMarkers
import com.egoriku.grodnoroads.shared.persistent.map.filtering.updateFiltering
import com.egoriku.grodnoroads.shared.persistent.map.location.defaultCity
import com.egoriku.grodnoroads.shared.persistent.map.location.updateDefaultCity
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.isShowCarCrash
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.isShowMediumSpeedCameras
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.isShowMobileCameras
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.isShowRoadIncidents
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.isShowStationaryCameras
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.isShowTrafficJam
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.isShowTrafficPolice
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.isShowWildAnimals
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.updateCarCrash
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.updateMediumSpeedCameras
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.updateMobileCameras
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.updateRoadIncidents
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.updateStationaryCameras
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.updateTrafficJam
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.updateTrafficPolice
import com.egoriku.grodnoroads.shared.persistent.map.mapinfo.updateWildAnimals
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.googleMapStyle
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.trafficJamOnMap
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.updateGoogleMapStyle
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.updateTrafficJamAppearance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class MapSettingsStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MapSettingsStore =
        object : MapSettingsStore, Store<Intent, StoreState, Nothing> by storeFactory.create(
            initialState = StoreState(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    dataStore.data
                        .map { pref ->
                            val showStationaryCameras = pref.isShowStationaryCameras
                            val showMediumSpeedCameras = pref.isShowMediumSpeedCameras
                            val showMobileCameras = pref.isShowMobileCameras
                            val showTrafficPolice = pref.isShowTrafficPolice
                            val showRoadIncidents = pref.isShowRoadIncidents
                            val showCarCrash = pref.isShowCarCrash
                            val showTrafficJam = pref.isShowTrafficJam
                            val showWildAnimals = pref.isShowWildAnimals

                            val isShowMarkers = listOf(
                                showStationaryCameras, showMediumSpeedCameras,
                                showMobileCameras, showTrafficPolice,
                                showRoadIncidents, showCarCrash,
                                showTrafficJam, showWildAnimals
                            )

                            val isAllMarkersDisabled = isShowMarkers.none { it }
                            val isAllMarkersEnabled = isShowMarkers.all { it }
                            val selectable = when {
                                isAllMarkersDisabled -> Selectable.AllDisabled
                                isAllMarkersEnabled -> Selectable.AllEnabled
                                else -> Selectable.Mixed
                            }

                            MapSettings(
                                markerFiltering = MarkerFiltering(current = pref.filteringMarkers),
                                mapInfo = MapInfo(
                                    stationaryCameras = StationaryCameras(isShow = showStationaryCameras),
                                    mediumSpeedCameras = MediumSpeedCameras(isShow = showMediumSpeedCameras),
                                    mobileCameras = MobileCameras(isShow = showMobileCameras),
                                    trafficPolice = TrafficPolice(isShow = showTrafficPolice),
                                    roadIncident = RoadIncident(isShow = showRoadIncidents),
                                    carCrash = CarCrash(isShow = showCarCrash),
                                    trafficJam = TrafficJam(isShow = showTrafficJam),
                                    wildAnimals = WildAnimals(isShow = showWildAnimals),
                                    selectable = selectable
                                ),
                                mapStyle = MapStyle(
                                    trafficJamOnMap = TrafficJamOnMap(isShow = pref.trafficJamOnMap),
                                    googleMapStyle = GoogleMapStyle(style = pref.googleMapStyle)
                                ),
                                locationInfo = LocationInfo(
                                    defaultCity = DefaultCity(current = pref.defaultCity),
                                ),
                                driveModeZoom = DriveModeZoom(
                                    mapZoomInCity = MapZoomInCity(current = pref.mapZoomInCity),
                                    mapZoomOutCity = MapZoomOutCity(current = pref.mapZoomOutCity)
                                )
                            )
                        }
                        .distinctUntilChanged()
                        .onEach {
                            dispatch(Message.NewSettings(it))
                            dispatch(Message.Loading(false))
                        }
                        .launchIn(this)
                }
                onIntent<Intent.Modify> { onCheckedChanged ->
                    val preference = onCheckedChanged.preference

                    launch {
                        dataStore.edit {
                            when (preference) {
                                is StationaryCameras -> updateStationaryCameras(preference.isShow)
                                is MediumSpeedCameras -> updateMediumSpeedCameras(preference.isShow)
                                is MobileCameras -> updateMobileCameras(preference.isShow)
                                is TrafficPolice -> updateTrafficPolice(preference.isShow)
                                is RoadIncident -> updateRoadIncidents(preference.isShow)
                                is CarCrash -> updateCarCrash(preference.isShow)
                                is TrafficJam -> updateTrafficJam(preference.isShow)
                                is WildAnimals -> updateWildAnimals(preference.isShow)
                                is SelectAll -> {
                                    updateStationaryCameras(preference.selectAll)
                                    updateMediumSpeedCameras(preference.selectAll)
                                    updateMobileCameras(preference.selectAll)
                                    updateTrafficPolice(preference.selectAll)
                                    updateRoadIncidents(preference.selectAll)
                                    updateCarCrash(preference.selectAll)
                                    updateTrafficJam(preference.selectAll)
                                    updateWildAnimals(preference.selectAll)
                                }

                                is TrafficJamOnMap -> updateTrafficJamAppearance(preference.isShow)
                                is GoogleMapStyle -> updateGoogleMapStyle(preference.style.type)

                                is DefaultCity -> updateDefaultCity(preference.current.cityName)

                                is MapZoomInCity -> updateMapZoomInCity(preference.current)
                                is MapZoomOutCity -> updateMapZoomOutsideCity(preference.current)
                                is MarkerFiltering -> updateFiltering(preference.current)
                            }
                        }
                    }
                }
                onIntent<Intent.OpenDialog> {
                    when (it.preference) {
                        is DefaultCity -> dispatch(
                            Message.NewDialogState(
                                mapDialogState = DefaultLocationDialogState(defaultCity = it.preference)
                            )
                        )

                        else -> throw UnsupportedOperationException("${it.preference} not supported")
                    }
                }
                onIntent<Intent.CloseDialog> {
                    dispatch(message = Message.NewDialogState(mapDialogState = None))
                }
                onIntent<Intent.Reset> {
                    val preference = it.preference

                    launch {
                        dataStore.edit {
                            when (preference) {
                                is MapZoomInCity -> updateMapZoomInCity(DEFAULT_MAP_ZOOM_IN_CITY)
                                is MapZoomOutCity -> updateMapZoomOutsideCity(
                                    DEFAULT_MAP_ZOOM_OUT_CITY
                                )

                                else -> Unit
                            }
                        }
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.NewSettings -> copy(mapSettings = message.mapSettings)
                    is Message.NewDialogState -> copy(mapDialogState = message.mapDialogState)
                    is Message.Loading -> copy(isLoading = message.isLoading)
                }
            }
        ) {}
}