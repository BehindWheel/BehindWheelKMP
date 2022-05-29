package com.egoriku.grodnoroads.screen.map.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.domain.model.MapEventType
import com.egoriku.grodnoroads.domain.model.MapEventType.RoadAccident
import com.egoriku.grodnoroads.domain.model.MapEventType.TrafficPolice
import com.egoriku.grodnoroads.domain.model.Source.App
import com.egoriku.grodnoroads.extension.common.ResultOf
import com.egoriku.grodnoroads.extension.logD
import com.egoriku.grodnoroads.screen.map.domain.MapEvent.*
import com.egoriku.grodnoroads.screen.map.data.MobileCameraRepository
import com.egoriku.grodnoroads.screen.map.data.ReportsRepository
import com.egoriku.grodnoroads.screen.map.data.StationaryCameraRepository
import com.egoriku.grodnoroads.screen.map.data.model.ReportsResponse
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Intent
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.Message.*
import com.egoriku.grodnoroads.screen.map.store.CamerasStoreFactory.State
import com.egoriku.grodnoroads.screen.map.store.util.mapAndMerge
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
            val mapEventType: MapEventType,
        ) : Intent
    }

    private sealed interface Message {
        data class OnStationary(val data: List<StationaryCamera>) : Message
        data class OnNewReports(val data: List<Reports>) : Message
        data class OnMobileCamera(val data: List<MobileCamera>) : Message
    }

    data class State(
        val stationaryCameras: List<StationaryCamera> = emptyList(),
        val reports: List<Reports> = emptyList(),
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
                            dispatch(OnNewReports(data = it))
                        }
                    }
                }
                onIntent<Intent.ReportAction> { action ->
                    launch {
                        val message = when (action.mapEventType) {
                            TrafficPolice -> "\uD83D\uDC6E"
                            RoadAccident -> "\uD83D\uDCA5"
                            else -> throw IllegalArgumentException()
                        }
                        reportsRepository.report(
                            ReportsResponse(
                                timestamp = System.currentTimeMillis(),
                                type = action.mapEventType.type,
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
                    is OnNewReports -> copy(reports = message.data)
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