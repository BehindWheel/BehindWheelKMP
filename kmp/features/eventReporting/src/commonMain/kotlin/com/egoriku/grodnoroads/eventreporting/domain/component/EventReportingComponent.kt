package com.egoriku.grodnoroads.eventreporting.domain.component

import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import com.egoriku.grodnoroads.location.LatLng

interface EventReportingComponent {

    fun report(params: ReportParams, latLng: LatLng)
}