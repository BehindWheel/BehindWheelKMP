package com.egoriku.grodnoroads.screen.map.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.extension.common.ResultOf
import com.egoriku.grodnoroads.extension.logD
import com.egoriku.grodnoroads.screen.map.data.MobileCameraRepository
import com.egoriku.grodnoroads.screen.map.data.ReportsRepository
import com.egoriku.grodnoroads.screen.map.data.StationaryCameraRepository
import com.egoriku.grodnoroads.screen.map.data.model.ReportsResponse
import com.egoriku.grodnoroads.screen.map.domain.AlertDialogState
import com.egoriku.grodnoroads.screen.map.domain.MapEvent.*
import com.egoriku.grodnoroads.screen.map.domain.MapEventType
import com.egoriku.grodnoroads.screen.map.domain.Source.App
import com.egoriku.grodnoroads.screen.map.store.MapEventsStoreFactory.Intent
import com.egoriku.grodnoroads.screen.map.store.MapEventsStoreFactory.Intent.ReportAction
import com.egoriku.grodnoroads.screen.map.store.MapEventsStoreFactory.State
import com.egoriku.grodnoroads.screen.map.store.util.mapAndMerge
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface MapEventsStore : Store<Intent, State, Nothing>

class MapEventsStoreFactory(
    private val storeFactory: StoreFactory,
    private val mobileCameraRepository: MobileCameraRepository,
    private val stationaryCameraRepository: StationaryCameraRepository,
    private val reportsRepository: ReportsRepository,
) {

    sealed interface Intent {
        data class ReportAction(
            val latLng: LatLng,
            val mapEventType: MapEventType,
        ) : Intent

        data class OpenMarkerInfoDialog(val reports: Reports) : Intent
        object CloseDialog : Intent
    }

    private sealed interface Message {
        data class OnStationary(val data: List<StationaryCamera>) : Message
        data class OnNewReports(val data: List<Reports>) : Message
        data class OnMobileCamera(val data: List<MobileCamera>) : Message

        data class OpenDialog(val dialog: AlertDialogState.Show) : Message
        data class CloseDialog(val dialog: AlertDialogState.Closed) : Message
    }

    data class State(
        val stationaryCameras: List<StationaryCamera> = emptyList(),
        val reports: List<Reports> = emptyList(),
        val mobileCamera: List<MobileCamera> = emptyList(),
        val alertDialogState: AlertDialogState = AlertDialogState.Closed
    )

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MapEventsStore =
        object : MapEventsStore, Store<Intent, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        subscribeForStationaryCameras {
                            dispatch(Message.OnStationary(data = it))
                        }
                    }
                    launch {
                        subscribeForMobileCameras {
                            dispatch(Message.OnMobileCamera(data = it))
                        }
                    }
                    launch {
                        subscribeForReports {
                            dispatch(Message.OnNewReports(data = it))
                        }
                    }
                }
                onIntent<ReportAction> { action ->
                    launch {
                        reportsRepository.report(
                            ReportsResponse(
                                timestamp = System.currentTimeMillis(),
                                type = action.mapEventType.type,
                                message = action.mapEventType.emoji,
                                shortMessage = "",
                                latitude = action.latLng.latitude,
                                longitude = action.latLng.longitude,
                                source = App.source
                            )
                        )
                    }
                }
                onIntent<Intent.OpenMarkerInfoDialog> { dialog ->
                    dispatch(
                        Message.OpenDialog(dialog = AlertDialogState.Show(dialog.reports))
                    )
                }
                onIntent<Intent.CloseDialog> {
                    dispatch(
                        Message.CloseDialog(dialog = AlertDialogState.Closed)
                    )
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.OnStationary -> copy(stationaryCameras = message.data)
                    is Message.OnNewReports -> copy(reports = message.data)
                    is Message.OnMobileCamera -> copy(mobileCamera = message.data)
                    is Message.OpenDialog -> copy(alertDialogState = message.dialog)
                    is Message.CloseDialog -> copy(alertDialogState = message.dialog)
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
                            position = LatLng(data.latitude, data.longitude),
                            speed = data.speed
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

    private suspend fun subscribeForReports(onLoaded: (List<Reports>) -> Unit) {
        reportsRepository.loadAsFlow().collect { result ->
            when (result) {
                is ResultOf.Success -> {
                    onLoaded(result.value.mapAndMerge())
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