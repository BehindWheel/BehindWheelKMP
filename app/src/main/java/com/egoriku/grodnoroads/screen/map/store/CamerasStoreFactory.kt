package com.egoriku.grodnoroads.screen.map.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.domain.model.EventType
import com.egoriku.grodnoroads.domain.usecase.CameraUseCase
import com.egoriku.grodnoroads.extension.common.ResultOf
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent.*
import com.egoriku.grodnoroads.screen.map.data.MobileCameraRepository
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Intent
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Message.*
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.State
import com.egoriku.grodnoroads.util.DateUtil
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface CamerasStore : Store<Intent, State, Nothing>

class CamerasStoreFactory(
    private val storeFactory: StoreFactory,
    private val cameraUseCase: CameraUseCase,
    private val mobileCameraRepository: MobileCameraRepository
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
        data class OnMobileCamera(val data: List<MobileCamera>) : Message
    }

    data class State(
        val stationaryCameras: List<StationaryCamera> = emptyList(),
        val userActions: List<UserActions> = emptyList(),
        val mobileCamera: List<MobileCamera> = emptyList(),
    )

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): CamerasStore =
        object : CamerasStore, Store<Intent, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dispatch(
                            StationaryLoaded(data = cameraUseCase.loadStationary())
                        )
                    }
                    launch {
                        cameraUseCase.usersActions().collect {
                            dispatch(UserActionsLoaded(data = it))
                        }
                    }
                    launch {
                        subscribeForMobileCameras { mobileCamera ->
                            dispatch(OnMobileCamera(data = mobileCamera))
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
                    is OnMobileCamera -> copy(mobileCamera = message.data)
                }
            }
        ) {}

    private suspend fun subscribeForMobileCameras(
        onLoaded: (List<MobileCamera>) -> Unit
    ) {
        mobileCameraRepository.loadAsFlow().collect { result ->
            when (result) {
                is ResultOf.Success -> {
                    val cameras = result.value.map { data ->
                        MobileCamera(
                            message = data.name,
                            position = LatLng(data.latitude, data.longitude)
                        )
                    }
                    onLoaded(cameras)
                }
                is ResultOf.Failure -> Firebase.crashlytics.recordException(result.exception)
            }
        }
    }

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