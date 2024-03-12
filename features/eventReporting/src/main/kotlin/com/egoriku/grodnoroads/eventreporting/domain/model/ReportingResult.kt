package com.egoriku.grodnoroads.eventreporting.domain.model

import com.egoriku.grodnoroads.shared.core.models.MapEventType
import com.google.android.gms.maps.model.LatLng

data class ReportingResult(
    val latLng: LatLng = LatLng(0.0, 0.0),
    val mapEventType: MapEventType,
    val shortMessage: String,
    val message: String
)