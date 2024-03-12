package com.egoriku.grodnoroads.eventreporting.domain.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.analytics.AnalyticsTracker
import com.egoriku.grodnoroads.eventreporting.domain.ReportActionModel
import com.egoriku.grodnoroads.eventreporting.domain.model.ReportingResult
import com.egoriku.grodnoroads.eventreporting.domain.repository.ReportingRepository
import com.egoriku.grodnoroads.eventreporting.domain.store.ReportingStore.Intent
import com.egoriku.grodnoroads.eventreporting.domain.store.ReportingStore.State
import com.egoriku.grodnoroads.shared.core.models.Source
import kotlinx.coroutines.launch

internal interface ReportingStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class ReportAction(val result: ReportingResult) : Intent
    }

    class State
}

internal class ReportingStoreFactory(
    private val storeFactory: StoreFactory,
    private val reportingRepository: ReportingRepository,
    private val analyticsTracker: AnalyticsTracker,
) {
    private val currentTime: Long
        get() = System.currentTimeMillis()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): ReportingStore =
        object : ReportingStore,
            Store<Intent, State, Nothing> by storeFactory.create<Intent, Unit, Nothing, State, Nothing>(
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = coroutineExecutorFactory {
                    onIntent<Intent.ReportAction> { data ->
                        launch {
                            val params = data.result

                            reportingRepository.report(
                                ReportActionModel(
                                    timestamp = currentTime,
                                    type = params.mapEventType.type,
                                    message = params.message,
                                    shortMessage = params.shortMessage,
                                    latitude = params.latLng.latitude,
                                    longitude = params.latLng.longitude,
                                    source = Source.App.source
                                )
                            )
                            analyticsTracker.eventReportAction(
                                eventType = params.mapEventType.type,
                                shortMessage = params.shortMessage
                            )
                        }
                    }
                }
            ) {}
}