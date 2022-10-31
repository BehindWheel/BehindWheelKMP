package com.egoriku.grodnoroads.map.domain.store.config

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.map.domain.model.MapInternalConfig
import com.egoriku.grodnoroads.map.domain.model.MapInternalConfig.MapInfo
import com.egoriku.grodnoroads.map.domain.store.config.MapConfigStore.*
import com.egoriku.grodnoroads.map.domain.store.config.MapConfigStore.Intent.CheckLocation
import com.egoriku.grodnoroads.map.domain.util.CityArea
import com.egoriku.grodnoroads.shared.appsettings.types.alert.alertDistance
import com.egoriku.grodnoroads.shared.appsettings.types.map.drivemode.mapZoomInCity
import com.egoriku.grodnoroads.shared.appsettings.types.map.drivemode.mapZoomOutCity
import com.egoriku.grodnoroads.shared.appsettings.types.map.mapinfo.*
import com.egoriku.grodnoroads.shared.appsettings.types.map.mapstyle.googleMapStyle
import com.egoriku.grodnoroads.shared.appsettings.types.map.mapstyle.trafficJamOnMap
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


internal class MapConfigStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MapConfigStore =
        object : MapConfigStore, Store<Intent, StoreState, Nothing> by storeFactory.create(
            initialState = StoreState(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dataStore.data.map { pref ->
                                MapInternalConfig(
                                    zoomLevelInCity = pref.mapZoomInCity,
                                    zoomLevelOutOfCity = pref.mapZoomOutCity,
                                    alertDistance = pref.alertDistance,
                                    mapInfo = MapInfo(
                                        showMobileCameras = pref.isShowMobileCameras,
                                        showStationaryCameras = pref.isShowStationaryCameras,
                                        showRoadIncident = pref.isShowRoadIncidents,
                                        showTrafficPolice = pref.isShowTrafficPolice,
                                        showTrafficJam = pref.isShowTrafficJam,
                                        showCarCrash = pref.isShowCarCrash,
                                        showWildAnimals = pref.isShowWildAnimals
                                    ),
                                    googleMapStyle = pref.googleMapStyle,
                                    trafficJanOnMap = pref.trafficJamOnMap
                                )
                            }.collect {
                                dispatch(Message.OnMapConfigInternal(it))
                            }
                    }
                }
                onIntent<CheckLocation> {
                    val latLng = it.latLng
                    val isInCity = when {
                        PolyUtil.containsLocation(latLng, CityArea.grodnoArea, false) -> true
                        PolyUtil.containsLocation(latLng, CityArea.berestovitca, false) -> true
                        PolyUtil.containsLocation(latLng, CityArea.skidel, false) -> true
                        PolyUtil.containsLocation(latLng, CityArea.ozery, false) -> true
                        PolyUtil.containsLocation(latLng, CityArea.porechie, false) -> true
                        PolyUtil.containsLocation(latLng, CityArea.volkovisk, false) -> true
                        else -> false
                    }

                    val zoomLevel = if (isInCity) {
                        state.mapInternalConfig.zoomLevelInCity
                    } else {
                        state.mapInternalConfig.zoomLevelOutOfCity
                    }

                    dispatch(Message.OnZoomLevel(zoomLevel))
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.OnMapConfigInternal -> copy(mapInternalConfig = message.mapConfig)
                    is Message.OnZoomLevel -> copy(zoomLevelDriveMode = message.zoomLevel)
                }
            }) {}
}