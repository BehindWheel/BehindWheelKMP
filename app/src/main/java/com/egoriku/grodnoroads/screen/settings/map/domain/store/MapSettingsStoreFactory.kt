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
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.MAP_ZOOM_OUT_CITY
import com.egoriku.grodnoroads.extension.put
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState.DefaultLocationDialogState
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapDialogState.None
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.*
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettings
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapSettings.*
import com.egoriku.grodnoroads.screen.settings.map.domain.store.MapSettingsStore.*
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
                        dispatch(Message.Loading)

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
                                        mapZoomInCity = MapZoomInCity(current = pref.mapZoomInCity),
                                        mapZoomOutCity = MapZoomOutCity(current = pref.mapZoomOutCity)
                                    )
                                )
                            }
                            .collect {
                                dispatch(Message.NewSettings(it))
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
                                is MapZoomOutCity -> MAP_ZOOM_OUT_CITY
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
                    is Message.Loading -> copy(isLoading = true)
                    is Message.NewSettings -> copy(
                        mapSettings = message.mapSettings,
                        isLoading = false
                    )
                    is Message.NewDialogState -> copy(mapDialogState = message.mapDialogState)
                }
            }
        ) {}
}