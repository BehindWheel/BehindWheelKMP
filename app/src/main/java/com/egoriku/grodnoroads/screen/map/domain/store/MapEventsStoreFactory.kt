package com.egoriku.grodnoroads.screen.map.domain.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.common.AnalyticsEvent.EVENT_REPORT_ACTION
import com.egoriku.grodnoroads.common.AnalyticsEvent.PARAM_EVENT_TYPE
import com.egoriku.grodnoroads.common.AnalyticsEvent.PARAM_SHORT_MESSAGE
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.map.domain.model.MapEvent.*
import com.egoriku.grodnoroads.map.domain.model.MapEventType
import com.egoriku.grodnoroads.map.domain.model.Source.App
import com.egoriku.grodnoroads.map.domain.model.report.ReportActionModel
import com.egoriku.grodnoroads.map.domain.repository.MobileCameraRepository
import com.egoriku.grodnoroads.map.domain.repository.ReportsRepository
import com.egoriku.grodnoroads.map.domain.repository.StationaryCameraRepository
import com.egoriku.grodnoroads.screen.map.domain.store.MapEventsStoreFactory.Intent
import com.egoriku.grodnoroads.screen.map.domain.store.MapEventsStoreFactory.Intent.ReportAction
import com.egoriku.grodnoroads.screen.map.domain.store.MapEventsStoreFactory.State
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
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
    private val firebaseAnalytics: FirebaseAnalytics,
) {

    sealed interface Intent {
        data class ReportAction(val params: Params) : Intent {
            data class Params(
                val latLng: LatLng,
                val mapEventType: MapEventType,
                val shortMessage: String,
                val message: String
            )
        }
    }

    private sealed interface Message {
        data class OnStationary(val data: List<StationaryCamera>) : Message
        data class OnNewReports(val data: List<Reports>) : Message
        data class OnMobileCamera(val data: List<MobileCamera>) : Message
    }

    data class State(
        val stationaryCameras: List<StationaryCamera> = emptyList(),
        val reports: List<Reports> = emptyList(),
        val mobileCamera: List<MobileCamera> = emptyList()
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
                onIntent<ReportAction> { data ->
                    launch {
                        val params = data.params

                        reportsRepository.report(
                            ReportActionModel(
                                timestamp = System.currentTimeMillis(),
                                type = params.mapEventType.type,
                                message = params.message,
                                shortMessage = params.shortMessage,
                                latitude = params.latLng.latitude,
                                longitude = params.latLng.longitude,
                                source = App.source
                            )
                        )
                        firebaseAnalytics.logEvent(EVENT_REPORT_ACTION) {
                            param(PARAM_EVENT_TYPE, params.mapEventType.type)
                            param(PARAM_SHORT_MESSAGE, params.shortMessage)
                        }
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.OnStationary -> copy(stationaryCameras = message.data)
                    is Message.OnNewReports -> copy(reports = message.data)
                    is Message.OnMobileCamera -> copy(mobileCamera = message.data)
                }
            }
        ) {}

    private suspend fun subscribeForMobileCameras(onLoaded: (List<MobileCamera>) -> Unit) {
        mobileCameraRepository.loadAsFlow().collect { result ->
            when (result) {
                is ResultOf.Success -> onLoaded(result.value)
                is ResultOf.Failure -> Firebase.crashlytics.recordException(result.exception).also {
                    logD(result.exception.message.toString())
                }
            }
        }
    }

    private suspend fun subscribeForReports(onLoaded: (List<Reports>) -> Unit) {
        reportsRepository.loadAsFlow().collect { result ->
            when (result) {
                is ResultOf.Success -> onLoaded(result.value)
                is ResultOf.Failure -> Firebase.crashlytics.recordException(result.exception).also {
                    logD(result.exception.message.toString())
                }
            }
        }
    }

    private suspend fun subscribeForStationaryCameras(onLoaded: (List<StationaryCamera>) -> Unit) {
        stationaryCameraRepository.loadAsFlow().collect { result ->
            when (result) {
                is ResultOf.Success -> onLoaded(result.value)
                is ResultOf.Failure -> Firebase.crashlytics.recordException(result.exception)
                    .also { logD(result.exception.message.toString()) }
            }
        }
    }
}