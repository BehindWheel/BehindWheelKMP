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
import com.egoriku.grodnoroads.shared.appsettings.types.alert.alertDistance
import com.egoriku.grodnoroads.shared.appsettings.types.map.drivemode.mapZoomInCity
import com.egoriku.grodnoroads.shared.appsettings.types.map.drivemode.mapZoomOutCity
import com.egoriku.grodnoroads.shared.appsettings.types.map.mapinfo.*
import com.egoriku.grodnoroads.shared.appsettings.types.map.mapstyle.googleMapStyle
import com.egoriku.grodnoroads.shared.appsettings.types.map.mapstyle.trafficJamOnMap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val loc = listOf(
    LatLng(53.677865, 23.7364759),
    LatLng(53.677258, 23.7517825),
    LatLng(53.6958691, 23.7523566),
    LatLng(53.7137621, 23.7905214),
    LatLng(53.7418072, 23.8205625),
    LatLng(53.7228865, 23.9034438),
    LatLng(53.6988061, 23.8997554),
    LatLng(53.6840719, 23.8965026),
    LatLng(53.6676634, 23.8967543),
    LatLng(53.6483532, 23.8946692),
    LatLng(53.6165424, 23.8885688),
    LatLng(53.5998739, 23.8418484),
    LatLng(53.6110952, 23.7947563),
    LatLng(53.6160009, 23.7492421),
    LatLng(53.6308808, 23.741084),
    LatLng(53.6543211, 23.7315001),
    LatLng(53.6689345, 23.7294389),
    LatLng(53.6777983, 23.7256919),
    LatLng(53.677865, 23.7364759)
)

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
                        dataStore.data
                            .map { pref ->
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
                    val isInCity = PolyUtil.containsLocation(
                        /* point = */ it.latLng,
                        /* polygon = */ loc,
                        /* geodesic = */ false
                    )
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
            }
        ) {}
}