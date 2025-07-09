package com.egoriku.grodnoroads.eventreporting.domain.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutorScope
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.crashlytics.shared.CrashlyticsTracker
import com.egoriku.grodnoroads.eventreporting.data.mapper.MobileCameraReportMapper
import com.egoriku.grodnoroads.eventreporting.data.mapper.ReportEventMapper
import com.egoriku.grodnoroads.eventreporting.data.repository.RateLimitExceededException
import com.egoriku.grodnoroads.eventreporting.domain.repository.ReportingRepository
import com.egoriku.grodnoroads.eventreporting.domain.store.ReportingStore.Intent
import com.egoriku.grodnoroads.eventreporting.domain.store.ReportingStore.Intent.ReportAction
import com.egoriku.grodnoroads.eventreporting.domain.store.ReportingStore.State
import com.egoriku.grodnoroads.extensions.common.ResultOf.Companion.fold
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.shared.analytics.AnalyticsTracker
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams.EventReport
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams.MobileCameraReport
import dev.gitlive.firebase.database.DatabaseException
import kotlinx.coroutines.launch

sealed interface Label {
    data object ReportingSuccess : Label
    data object ReportingTooOften : Label
    data object ReportingDisabled : Label
    data object GeneralError : Label
}

internal typealias ReportingScope = CoroutineExecutorScope<State, Nothing, Unit, Label>

internal interface ReportingStore : Store<Intent, State, Label> {

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
    private val analyticsTracker: AnalyticsTracker,
    private val crashlyticsTracker: CrashlyticsTracker
) {
    fun create(): ReportingStore = object :
        ReportingStore,
        Store<Intent, State, Label> by storeFactory.create<Intent, Unit, Nothing, State, Label>(
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = coroutineExecutorFactory {
                onIntent<ReportAction> { data ->
                    launch {
                        when (val params = data.params) {
                            is EventReport -> reportEvent(data, params)
                            is MobileCameraReport -> reportMobileCamera(data, params)
                        }
                    }
                }
            }
        ) {}

    private suspend fun ReportingScope.reportEvent(
        data: ReportAction,
        params: EventReport
    ) {
        reportingRepository
            .reportEvent(
                reportsDTO = ReportEventMapper(
                    latLng = data.latLng,
                    eventReport = params
                )
            )
            .fold(
                onSuccess = {
                    publish(Label.ReportingSuccess)

                    analyticsTracker.eventReportAction(
                        eventType = params.mapEventType.type,
                        shortMessage = params.shortMessage
                    )
                },
                onFailure = { exception ->
                    crashlyticsTracker.recordException(exception = exception)

                    when (exception) {
                        is DatabaseException -> publish(Label.ReportingDisabled)
                        is RateLimitExceededException -> publish(Label.ReportingTooOften)
                        else -> publish(Label.GeneralError)
                    }
                }
            )
    }

    private suspend fun ReportingScope.reportMobileCamera(
        data: ReportAction,
        params: MobileCameraReport
    ) {
        reportingRepository
            .reportMobileCamera(
                mobileCameraDTO = MobileCameraReportMapper(
                    latLng = data.latLng,
                    cameraReport = params
                )
            ).fold(
                onSuccess = {
                    publish(Label.ReportingSuccess)

                    analyticsTracker.mobileCameraReport()
                },
                onFailure = { exception ->
                    crashlyticsTracker.recordException(exception = exception)

                    when (exception) {
                        is DatabaseException -> publish(Label.ReportingDisabled)
                        is RateLimitExceededException -> publish(Label.ReportingTooOften)
                        else -> publish(Label.GeneralError)
                    }
                }
            )
    }
}
