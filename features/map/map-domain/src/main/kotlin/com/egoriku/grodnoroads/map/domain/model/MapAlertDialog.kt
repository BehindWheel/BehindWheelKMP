package com.egoriku.grodnoroads.map.domain.model

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports

sealed interface MapAlertDialog {

    data class MarkerInfoDialog(val reports: Reports) : MapAlertDialog
    data class PoliceDialog(val currentLatLng: LatLng) : MapAlertDialog
    data class RoadIncidentDialog(val currentLatLng: LatLng) : MapAlertDialog
    data object None : MapAlertDialog
}