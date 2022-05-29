package com.egoriku.grodnoroads.screen.map.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.screen.map.domain.AppMode
import com.egoriku.grodnoroads.screen.map.domain.LocationState
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.*
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Intent.*
import com.egoriku.grodnoroads.util.ResourceProvider
import com.egoriku.grodnoroads.util.location.LocationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface LocationStore : Store<Intent, State, Label>

class LocationStoreFactory(
    private val storeFactory: StoreFactory,
    private val locationHelper: LocationHelper,
    private val resourceProvider: ResourceProvider
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
                onIntent<StartLocationUpdates> {
                    locationHelper.startLocationUpdates()
                    dispatch(Message.ChangeAppMode(appMode = AppMode.Drive))

                    launch {
                        locationHelper.lastLocation.collect {
                            dispatch(Message.OnNewLocation(locationState = it))
                        }
                    }
                }
                onIntent<StopLocationUpdates> {
                    locationHelper.stopLocationUpdates()

                    dispatch(Message.ChangeAppMode(appMode = AppMode.Map))
                    dispatch(Message.OnNewLocation(locationState = LocationState.None))
                }
                onIntent<DisabledLocation> {
                    publish(Label.ShowToast(message = resourceProvider.locationDisabled))
                }
            },
            reducer = { message: Message ->
                when (message) {
                    is Message.OnNewLocation -> copy(locationState = message.locationState)
                    is Message.ChangeAppMode -> copy(appMode = message.appMode)
                }
            }
        ) {}
}
