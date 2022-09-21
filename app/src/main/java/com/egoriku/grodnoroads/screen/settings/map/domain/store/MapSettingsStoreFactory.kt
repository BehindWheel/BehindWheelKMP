package com.egoriku.grodnoroads.screen.settings.map.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.common.datastore.DataFlow.defaultCity
import com.egoriku.grodnoroads.common.datastore.DataFlow.googleMapStyle
import com.egoriku.grodnoroads.common.datastore.DataFlow.isShowCarCrash
import com.egoriku.grodnoroads.common.datastore.DataFlow.isShowMobileCameras
import com.egoriku.grodnoroads.common.datastore.DataFlow.isShowRoadIncidents
import com.egoriku.grodnoroads.common.datastore.DataFlow.isShowStationaryCameras
import com.egoriku.grodnoroads.common.datastore.DataFlow.isShowTrafficJam
import com.egoriku.grodnoroads.common.datastore.DataFlow.isShowTrafficPolice
import com.egoriku.grodnoroads.common.datastore.DataFlow.isShowWildAnimals
import com.egoriku.grodnoroads.common.datastore.DataFlow.mapZoomInCity
import com.egoriku.grodnoroads.common.datastore.DataFlow.mapZoomOutCity
import com.egoriku.grodnoroads.common.datastore.DataFlow.trafficJamOnMap
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.DEFAULT_CITY
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.GOOGLE_MAP_STYLE
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_CAR_CRASH_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_INCIDENT_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_MOBILE_CAMERAS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_STATIONARY_CAMERAS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_TRAFFIC_JAM_APPEARANCE
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_TRAFFIC_JAM_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_TRAFFIC_POLICE_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_WILD_ANIMALS_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.MAP_ZOOM_IN_CITY
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.MAP_ZOOM_OUTSIDE_CITY
import com.egoriku.grodnoroads.extension.put
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState.DefaultLocationDialogState
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState.MapZoomInCityDialogState
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState.MapZoomOutCityDialogState
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState.None
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.CarCrash
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.GoogleMapStyle
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.MapZoomInCity
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.MapZoomOutCity
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.MobileCameras
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.RoadIncident
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.StationaryCameras
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.TrafficJam
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.TrafficJamOnMap
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.TrafficPolice
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.WildAnimals
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettings
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettings.DriveModeZoom
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettings.LocationInfo
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettings.MapInfo
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettings.MapStyle
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.Intent
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.Message
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.StoreState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MapSettingsStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MapSettingsStore =
        object : MapSettingsStore, Store<Intent, StoreState, Nothing> by storeFactory.create(
            initialState = StoreState(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dispatch(Message.Loading(true))

                        dataStore.data
                            .map { pref ->
                                MapSettings(
                                    mapInfo = MapInfo(
                                        stationaryCameras = StationaryCameras(isShow = pref.isShowStationaryCameras),
                                        mobileCameras = MobileCameras(isShow = pref.isShowMobileCameras),
                                        trafficPolice = TrafficPolice(isShow = pref.isShowTrafficPolice),
                                        roadIncident = RoadIncident(isShow = pref.isShowRoadIncidents),
                                        carCrash = CarCrash(isShow = pref.isShowCarCrash),
                                        trafficJam = TrafficJam(isShow = pref.isShowTrafficJam),
                                        wildAnimals = WildAnimals(isShow = pref.isShowWildAnimals),
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
                            .collect {
                                dispatch(Message.NewSettings(it))
                                dispatch(Message.Loading(false))
                            }
                    }
                }
                onIntent<Intent.Modify> { onCheckedChanged ->
                    val preference = onCheckedChanged.preference

                    launch {
                        dataStore.put(
                            key = when (preference) {
                                is StationaryCameras -> IS_SHOW_STATIONARY_CAMERAS
                                is MobileCameras -> IS_SHOW_MOBILE_CAMERAS
                                is TrafficPolice -> IS_SHOW_TRAFFIC_POLICE_EVENTS
                                is RoadIncident -> IS_SHOW_INCIDENT_EVENTS
                                is CarCrash -> IS_SHOW_CAR_CRASH_EVENTS
                                is TrafficJam -> IS_SHOW_TRAFFIC_JAM_EVENTS
                                is WildAnimals -> IS_SHOW_WILD_ANIMALS_EVENTS

                                is TrafficJamOnMap -> IS_SHOW_TRAFFIC_JAM_APPEARANCE
                                is GoogleMapStyle -> GOOGLE_MAP_STYLE
                                is DefaultCity -> DEFAULT_CITY
                                is MapZoomInCity -> MAP_ZOOM_IN_CITY
                                is MapZoomOutCity -> MAP_ZOOM_OUTSIDE_CITY
                            }.name,
                            value = when (preference) {
                                is StationaryCameras -> preference.isShow
                                is MobileCameras -> preference.isShow
                                is TrafficPolice -> preference.isShow
                                is RoadIncident -> preference.isShow
                                is CarCrash -> preference.isShow
                                is TrafficJam -> preference.isShow
                                is WildAnimals -> preference.isShow

                                is TrafficJamOnMap -> preference.isShow
                                is GoogleMapStyle -> preference.style.type
                                is DefaultCity -> preference.current.cityName
                                is MapZoomInCity -> preference.current
                                is MapZoomOutCity -> preference.current
                            }
                        )
                    }
                }
                onIntent<Intent.OpenDialog> {
                    when (it.preference) {
                        is DefaultCity -> dispatch(
                            Message.NewDialogState(
                                mapDialogState = DefaultLocationDialogState(defaultCity = it.preference)
                            )
                        )

                        is MapZoomInCity -> dispatch(
                            Message.NewDialogState(
                                mapDialogState = MapZoomInCityDialogState(mapZoomInCity = it.preference)
                            )
                        )

                        is MapZoomOutCity -> dispatch(
                            Message.NewDialogState(
                                mapDialogState = MapZoomOutCityDialogState(mapZoomOutCity = it.preference)
                            )
                        )

                        else -> throw UnsupportedOperationException("${it.preference} not supported")
                    }
                }
                onIntent<Intent.CloseDialog> {
                    dispatch(message = Message.NewDialogState(mapDialogState = None))
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