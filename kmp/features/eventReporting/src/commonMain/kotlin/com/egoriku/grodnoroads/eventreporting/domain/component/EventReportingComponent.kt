package com.egoriku.grodnoroads.eventreporting.domain.component

import com.egoriku.grodnoroads.eventreporting.domain.store.Label
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import kotlinx.coroutines.flow.Flow

interface EventReportingComponent {

    val labels: Flow<Label>

    fun report(params: ReportParams, latLng: LatLng)
}
