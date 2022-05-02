package com.egoriku.grodnoroads.screen.map.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.domain.model.Camera
import com.egoriku.grodnoroads.domain.model.MapEvent
import com.egoriku.grodnoroads.domain.model.UserActionType
import com.egoriku.grodnoroads.domain.usecase.CameraUseCase
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Intent
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Message.StationaryLoaded
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Message.UserActionsLoaded
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.State
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface CamerasStore : Store<Intent, State, Nothing>

class CamerasStoreFactory(
    private val storeFactory: StoreFactory,
    private val cameraUseCase: CameraUseCase
) {

    sealed class Intent {
        data class ReportAction(
            val latLng: LatLng,
            val type: UserActionType
        ) : Intent()
    }

    private sealed class Message {
        data class StationaryLoaded(val data: List<Camera>) : Message()
        data class UserActionsLoaded(val data: List<MapEvent>) : Message()
    }

    data class State(
        val stationaryCameras: List<Camera> = emptyList(),
        val userActions: List<MapEvent> = emptyList()
    )

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): CamerasStore =
        object : CamerasStore, Store<Intent, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dispatch(StationaryLoaded(data = cameraUseCase.loadStationary()))

                        cameraUseCase.usersActions().collect {
                            dispatch(UserActionsLoaded(data = it))
                        }
                    }
                }
                onIntent<Intent.ReportAction> {
                    launch {
                        cameraUseCase.reportAction(type = it.type, latLng = it.latLng)
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is StationaryLoaded -> copy(stationaryCameras = message.data)
                    is UserActionsLoaded -> copy(userActions = message.data)
                }
            }
        ) {}
}