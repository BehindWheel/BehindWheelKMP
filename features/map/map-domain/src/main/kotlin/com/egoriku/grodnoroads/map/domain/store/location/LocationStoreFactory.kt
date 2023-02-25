package com.egoriku.grodnoroads.map.domain.store.location

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.location.LocationHelper
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore.*
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore.Intent.*
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.shared.appsettings.types.map.location.defaultCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal class LocationStoreFactory(
    private val storeFactory: StoreFactory,
    private val locationHelper: LocationHelper,
    private val dataStore: DataStore<Preferences>
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): LocationStore =
        object : LocationStore, Store<Intent, State, Label> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        locationHelper.getLastKnownLocation()?.run {
                            dispatch(
                                Message.OnNewLocation(
                                    lastLocation = LastLocation(latLng, bearing, speed)
                                )
                            )
                        }
                    }
                    dataStore.data
                        .map { it.defaultCity }
                        .distinctUntilChanged()
                        .onEach {
                            dispatch(
                                Message.OnNewLocation(
                                    lastLocation = LastLocation(it.latLng, 0f, 0)
                                )
                            )
                        }
                        .launchIn(this)
                }
                onIntent<StartLocationUpdates> {
                    locationHelper.startLocationUpdates()

                    dispatch(Message.OnNewLocation(LastLocation.None))

                    launch {
                        locationHelper.lastLocationFlow
                            .filterNotNull()
                            .map { LastLocation(it.latLng, it.bearing, it.speed) }
                            .collect {
                                dispatch(Message.OnNewLocation(lastLocation = it))

                                publish(Label.NewLocation(it.latLng))
                            }
                    }
                }
                onIntent<StopLocationUpdates> {
                    locationHelper.stopLocationUpdates()
                }
                onIntent<DisabledLocation> {
                    publish(Label.ShowToast(resId = R.string.toast_location_disabled))
                }
                onIntent<SetUserLocation> {
                    dispatch(
                        Message.OnUserLocation(
                            lastLocation = LastLocation(
                                latLng = it.latLng,
                                bearing = state.lastLocation.bearing,
                                speed = state.lastLocation.speed
                            )
                        )
                    )
                }
                onIntent<InvalidateLocation> {
                    dispatch(Message.OnNewLocation(lastLocation = state.userLocation))
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.OnNewLocation -> copy(lastLocation = message.lastLocation)
                    is Message.OnUserLocation -> copy(userLocation = message.lastLocation)
                }
            }
        ) {}
}
