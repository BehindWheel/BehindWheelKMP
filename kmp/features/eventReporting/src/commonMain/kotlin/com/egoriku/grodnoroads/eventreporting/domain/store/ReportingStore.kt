package com.egoriku.grodnoroads.eventreporting.domain.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.eventreporting.data.mapper.MobileCameraReportMapper
import com.egoriku.grodnoroads.eventreporting.data.mapper.ReportEventMapper
import com.egoriku.grodnoroads.eventreporting.domain.repository.ReportingRepository
import com.egoriku.grodnoroads.eventreporting.domain.store.ReportingStore.Intent
import com.egoriku.grodnoroads.eventreporting.domain.store.ReportingStore.State
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import kotlinx.coroutines.launch

internal interface ReportingStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class ReportAction(
            val params: ReportParams,
            val latLng: LatLng
        ) : Intent
    }

    class State
}

internal class ReportingStoreFactory(
    private val storeFactory: StoreFactory,
    private val reportingRepository: ReportingRepository,
    // private val analyticsTracker: AnalyticsTracker,
) {
    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): ReportingStore = object : ReportingStore,
        Store<Intent, State, Nothing> by storeFactory.create<Intent, Unit, Nothing, State, Nothing>(
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = coroutineExecutorFactory {
                onIntent<Intent.ReportAction> { data ->
                    launch {
                        when (val params = data.params) {
                            is ReportParams.EventReport -> {
                                reportingRepository.reportEvent(
                                    reportsDTO = ReportEventMapper(
                                        latLng = data.latLng,
                                        eventReport = params
                                    )
                                )
                                // TODO: use analytics
                                /* analyticsTracker.eventReportAction(
                                    eventType = params.mapEventType.type,
                                    shortMessage = params.shortMessage
                                )*/
                            }
                            is ReportParams.MobileCameraReport -> {
                                reportingRepository.reportMobileCamera(
                                    mobileCameraDTO = MobileCameraReportMapper(
                                        latLng = data.latLng,
                                        cameraReport = params
                                    )
                                )
                                // analyticsTracker.mobileCameraReport()
                            }
                        }
                    }
                }
            }
        ) {}
}