package com.egoriku.grodnoroads.screen.map.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.common.datastore.DataFlow.defaultCity
import com.egoriku.grodnoroads.screen.map.domain.model.AppMode
import com.egoriku.grodnoroads.screen.map.domain.model.LocationState
import com.egoriku.grodnoroads.screen.map.domain.store.LocationStoreFactory.Intent
import com.egoriku.grodnoroads.screen.map.domain.store.LocationStoreFactory.Intent.DisabledLocation
import com.egoriku.grodnoroads.screen.map.domain.store.LocationStoreFactory.Intent.StartLocationUpdates
import com.egoriku.grodnoroads.screen.map.domain.store.LocationStoreFactory.Intent.StopLocationUpdates
import com.egoriku.grodnoroads.screen.map.domain.store.LocationStoreFactory.Label
import com.egoriku.grodnoroads.screen.map.domain.store.LocationStoreFactory.State
import com.egoriku.grodnoroads.util.ResourceProvider
import com.egoriku.grodnoroads.util.location.LocationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface LocationStore : Store<Intent, State, Label>

class LocationStoreFactory(
    private val storeFactory: StoreFactory,
    private val locationHelper: LocationHelper,
    private val resourceProvider: ResourceProvider,
    private val datastore: DataStore<Preferences>
) {

    sealed interface Intent {
        object StartLocationUpdates : Intent
        object StopLocationUpdates : Intent
        object DisabledLocation : Intent
    }

    private sealed interface Message {
        data class ChangeAppMode(val appMode: AppMode) : Message
        data class OnNewLocation(val locationState: LocationState) : Message
    }

    sealed interface Label {
        object None : Label
        data class ShowToast(val message: String) : Label
    }

    data class State(
        val locationState: LocationState = LocationState.None,
        val appMode: AppMode = AppMode.Map
    )

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): LocationStore =
        object : LocationStore, Store<Intent, State, Label> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        val lastKnownLocation = locationHelper.getLastKnownLocation()

                        if (lastKnownLocation != null) {
                            dispatch(
                                Message.OnNewLocation(locationState = lastKnownLocation)
                            )
                        } else {
                            datastore.data.collect {
                                dispatch(
                                    Message.OnNewLocation(
                                        locationState = LocationState(
                                            latLng = it.defaultCity.latLng,
                                            bearing = 0.0f,
                                            speed = 0
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
                onIntent<StartLocationUpdates> {
                    locationHelper.startLocationUpdates()
                    dispatch(Message.ChangeAppMode(appMode = AppMode.Drive))

                    launch {
                        locationHelper.lastLocationFlow.collect {
                            dispatch(Message.OnNewLocation(locationState = it))
                        }
                    }
                }
                onIntent<StopLocationUpdates> {
                    locationHelper.stopLocationUpdates()

                    dispatch(Message.ChangeAppMode(appMode = AppMode.Map))
                }
                onIntent<DisabledLocation> {
                    publish(Label.ShowToast(message = resourceProvider.locationDisabled))
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.OnNewLocation -> copy(locationState = message.locationState)
                    is Message.ChangeAppMode -> copy(appMode = message.appMode)
                }
            }
        ) {}
}
