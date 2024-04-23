package com.egoriku.grodnoroads.eventreporting.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.egoriku.grodnoroads.eventreporting.domain.model.ReportParams
import com.egoriku.grodnoroads.eventreporting.domain.store.ReportingStore
import com.egoriku.grodnoroads.eventreporting.domain.store.ReportingStore.Intent
import com.google.android.gms.maps.model.LatLng
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildEventReportingComponent(
    componentContext: ComponentContext,
): EventReportingComponent = EventReportingComponentImpl(componentContext = componentContext)

internal class EventReportingComponentImpl(
    componentContext: ComponentContext,
) : EventReportingComponent, ComponentContext by componentContext, KoinComponent {

    private val reportingStore: ReportingStore = instanceKeeper.getStore(::get)

    override fun report(params: ReportParams, latLng: LatLng) {
        reportingStore.accept(Intent.ReportAction(params, latLng))
    }
}