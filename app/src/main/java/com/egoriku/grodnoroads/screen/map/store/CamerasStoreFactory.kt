package com.egoriku.grodnoroads.screen.map.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.domain.model.EventType
import com.egoriku.grodnoroads.domain.usecase.CameraUseCase
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent.StationaryCamera
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent.UserActions
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Intent
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Message.StationaryLoaded
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Message.UserActionsLoaded
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.State
import com.egoriku.grodnoroads.util.DateUtil
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface CamerasStore : Store<Intent, State, Nothing>

class CamerasStoreFactory(
    private val storeFactory: StoreFactory,
    private val cameraUseCase: CameraUseCase
) {

    sealed interface Intent {
        data class ReportAction(
            val latLng: LatLng,
            val eventType: EventType
        ) : Intent
    }

    private sealed interface Message {
        data class StationaryLoaded(val data: List<StationaryCamera>) : Message
        data class UserActionsLoaded(val data: List<UserActions>) : Message
    }

    data class State(
        val stationaryCameras: List<StationaryCamera> = emptyList(),
        val userActions: List<UserActions> = emptyList()
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
                onIntent<Intent.ReportAction> { action ->
                    launch {
                        dispatch(
                            UserActionsLoaded(
                                data = state.userActions + buildInstantAction(action)
                            )
                        )

                        cameraUseCase.reportAction(type = action.eventType, latLng = action.latLng)
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

    private fun buildInstantAction(action: Intent.ReportAction) =
        UserActions(
            time = DateUtil.formatToTime(date = System.currentTimeMillis()),
            message = when (action.eventType) {
                EventType.Police -> "\uD83D\uDC6E"
                EventType.Accident -> "\uD83D\uDCA5"
                else -> throw IllegalArgumentException("reporting ${action.eventType} is not supporting")
            },
            position = action.latLng,
            eventType = action.eventType
        )
}