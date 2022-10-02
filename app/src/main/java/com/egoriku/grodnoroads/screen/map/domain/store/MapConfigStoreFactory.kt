package com.egoriku.grodnoroads.screen.map.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.common.datastore.DataFlow.mapZoomInCity
import com.egoriku.grodnoroads.screen.map.domain.model.MapConfig
import com.egoriku.grodnoroads.screen.map.domain.store.MapConfigStore.*
import com.egoriku.grodnoroads.screen.map.domain.store.MapConfigStore.Intent.CheckLocation
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.PolyUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val loc = listOf(
    LatLng(23.7364759, 53.677865),
    LatLng(23.7517825, 53.677258),
    LatLng(23.7523566, 53.6958691),
    LatLng(23.7905214, 53.7137621),
    LatLng(23.8205625, 53.7418072),
    LatLng(23.9034438, 53.7228865),
    LatLng(23.8997554, 53.6988061),
    LatLng(23.8965026, 53.6840719),
    LatLng(23.8967543, 53.6676634),
    LatLng(23.8946692, 53.6483532),
    LatLng(23.8885688, 53.6165424),
    LatLng(23.8418484, 53.5998739),
    LatLng(23.7947563, 53.6110952),
    LatLng(23.7492421, 53.6160009),
    LatLng(23.741084, 53.6308808),
    LatLng(23.7315001, 53.6543211),
    LatLng(23.7294389, 53.6689345),
    LatLng(23.7256919, 53.6777983),
    LatLng(23.7364759, 53.677865)
)

class MapConfigStoreFactory(
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
                                MapConfig(
                                    zoomLevel = pref.mapZoomInCity
                                )
                            }.collect {
                                dispatch(Message.OnMapConfig(it))
                            }
                    }
                }
                onIntent<CheckLocation> {
                    val isInCity = PolyUtil.containsLocation(
                        /* point = */ it.latLng,
                        /* polygon = */ loc,
                        /* geodesic = */ false
                    )
                    dispatch(
                        Message.OnMapConfig(
                            MapConfig(
                                zoomLevel = state.mapConfig.zoomLevel
                            )
                        )
                    )
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.OnMapConfig -> copy(mapConfig = message.mapConfig)
                }
            }
        ) {}
}