package com.egoriku.grodnoroads.eventreporting.domain.component

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams

interface EventReportingComponent {

    fun report(params: ReportParams, latLng: LatLng)
}