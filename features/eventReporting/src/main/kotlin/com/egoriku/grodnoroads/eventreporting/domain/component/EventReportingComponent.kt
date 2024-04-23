package com.egoriku.grodnoroads.eventreporting.domain.component

import com.egoriku.grodnoroads.eventreporting.domain.model.ReportParams
import com.google.android.gms.maps.model.LatLng

interface EventReportingComponent {

    fun report(params: ReportParams, latLng: LatLng)
}