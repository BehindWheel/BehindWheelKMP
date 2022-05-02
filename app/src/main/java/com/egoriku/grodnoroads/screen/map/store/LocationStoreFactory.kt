package com.egoriku.grodnoroads.screen.map.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.domain.model.AppMode
import com.egoriku.grodnoroads.domain.model.UserPosition
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Intent
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Intent.StartLocationUpdates
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Intent.StopLocationUpdates
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.State
import com.egoriku.grodnoroads.util.location.LocationHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface LocationStore : Store<Intent, State, Nothing>

class LocationStoreFactory(
    private val storeFactory: StoreFactory,
    private val locationHelper: LocationHelper
) {

    sealed class Intent {
        object StartLocationUpdates : Intent()
        object StopLocationUpdates : Intent()
    }

    private sealed class Message {
        data class ChangeAppMode(val appMode: AppMode) : Message()
        data class OnNewLocation(val userPosition: UserPosition) : Message()
    }

    data class State(
        val userPosition: UserPosition = UserPosition.None,
        val appMode: AppMode = AppMode.Map
    )

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): LocationStore =
        object : LocationStore, Store<Intent, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onIntent<StartLocationUpdates> {
                    locationHelper.startLocationUpdates()
                    dispatch(Message.ChangeAppMode(appMode = AppMode.Drive))

                    launch {
                        locationHelper.lastLocation.collect {
                            dispatch(Message.OnNewLocation(userPosition = it))
                        }
                    }

                }
                onIntent<StopLocationUpdates> {
                    locationHelper.stopLocationUpdates()

                    dispatch(Message.ChangeAppMode(appMode = AppMode.Map))
                    dispatch(Message.OnNewLocation(userPosition = UserPosition.None))
                }
            },
            reducer = { message: Message ->
                when (message) {
                    is Message.OnNewLocation -> copy(userPosition = message.userPosition)
                    is Message.ChangeAppMode -> copy(appMode = message.appMode)
                }
            }
        ) {}
}
