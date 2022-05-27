package com.egoriku.grodnoroads.screen.map.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.domain.model.EventType
import com.egoriku.grodnoroads.domain.model.EventType.Companion.eventFromString
import com.egoriku.grodnoroads.domain.model.Source.App
import com.egoriku.grodnoroads.domain.model.Source.Companion.sourceFromString
import com.egoriku.grodnoroads.extension.appendIfNotEmpty
import com.egoriku.grodnoroads.extension.common.ResultOf
import com.egoriku.grodnoroads.extension.logD
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent.*
import com.egoriku.grodnoroads.screen.map.data.MobileCameraRepository
import com.egoriku.grodnoroads.screen.map.data.ReportsRepository
import com.egoriku.grodnoroads.screen.map.data.StationaryCameraRepository
import com.egoriku.grodnoroads.screen.map.data.model.ReportsResponse
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Intent
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Message.*
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.State
import com.egoriku.grodnoroads.screen.map.store.util.mergeActions
import com.egoriku.grodnoroads.util.DateUtil
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface CamerasStore : Store<Intent, State, Nothing>

class CamerasStoreFactory(
    private val storeFactory: StoreFactory,
    private val mobileCameraRepository: MobileCameraRepository,
    private val stationaryCameraRepository: StationaryCameraRepository,
    private val reportsRepository: ReportsRepository,
) {

    sealed interface Intent {
        data class ReportAction(
            val latLng: LatLng,
            val eventType: EventType,
        ) : Intent
    }

    private sealed interface Message {
        data class OnStationary(val data: List<StationaryCamera>) : Message
        data class OnUserActions(val data: List<UserActions>) : Message
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
                        subscribeForStationaryCameras {
                            dispatch(OnStationary(data = it))
                        }
                    }
                    launch {
                        subscribeForMobileCameras {
                            dispatch(OnMobileCamera(data = it))
                        }
                    }
                    launch {
                        subscribeForReports {
                            dispatch(OnUserActions(data = it))
                        }
                    }
                }
                onIntent<Intent.ReportAction> { action ->
                    launch {
                        val message = when (action.eventType) {
                            EventType.Police -> "\uD83D\uDC6E"
                            EventType.Accident -> "\uD83D\uDCA5"
                            else -> throw IllegalArgumentException()
                        }
                        reportsRepository.report(
                            ReportsResponse(
                                timestamp = System.currentTimeMillis(),
                                type = action.eventType.type,
                                message = message,
                                shortMessage = "",
                                latitude = action.latLng.latitude,
                                longitude = action.latLng.longitude,
                                source = App.source
                            )
                        )
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is OnStationary -> copy(stationaryCameras = message.data)
                    is OnUserActions -> copy(userActions = message.data)
                    is OnMobileCamera -> copy(mobileCamera = message.data)
                }
            }
        ) {}

    private suspend fun subscribeForMobileCameras(onLoaded: (List<MobileCamera>) -> Unit) {
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
                is ResultOf.Failure -> Firebase.crashlytics.recordException(result.exception).also {
                    logD(result.exception.message.toString())
                }
            }
        }
    }

    private suspend fun subscribeForReports(onLoaded: (List<UserActions>) -> Unit) {
        reportsRepository.loadAsFlow().collect { result ->
            when (result) {
                is ResultOf.Success -> {
                    val cameras = result.value
                        .map { data ->
                            val eventType = eventFromString(data.type)
                            val shortMessage = when (eventType) {
                                EventType.Police -> buildString {
                                    append("\uD83D\uDC6E")
                                    appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
                                }
                                EventType.Accident -> buildString {
                                    append("\uD83D\uDCA5")
                                    appendIfNotEmpty(data.shortMessage, " (${data.shortMessage})")
                                }
                                else -> data.shortMessage
                            }

                            UserActions(
                                message = data.message,
                                shortMessage = shortMessage,
                                source = sourceFromString(data.source),
                                position = LatLng(data.latitude, data.longitude),
                                time = DateUtil.formatToTime(data.timestamp),
                                eventType = eventType
                            )
                        }
                        .mergeActions()

                    onLoaded(cameras)
                }
                is ResultOf.Failure -> Firebase.crashlytics.recordException(result.exception).also {
                    logD(result.exception.message.toString())
                }
            }
        }
    }

    private suspend fun subscribeForStationaryCameras(onLoaded: (List<StationaryCamera>) -> Unit) {
        stationaryCameraRepository.loadAsFlow().collect { result ->
            when (result) {
                is ResultOf.Success -> {
                    val cameras = result.value.map { data ->
                        StationaryCamera(
                            message = data.message,
                            speed = data.speed,
                            position = LatLng(data.latitude, data.longitude)
                        )
                    }
                    onLoaded(cameras)
                }
                is ResultOf.Failure -> Firebase.crashlytics.recordException(result.exception)
                    .also { logD(result.exception.message.toString()) }
            }
        }
    }
}