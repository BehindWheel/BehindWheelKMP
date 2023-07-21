package com.egoriku.grodnoroads.map.domain.model

import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.google.android.gms.maps.model.LatLng

sealed interface MapAlertDialog {

    data class MarkerInfoDialog(val reports: Reports) : MapAlertDialog
    data class PoliceDialog(val currentLatLng: LatLng) : MapAlertDialog
    data class RoadIncidentDialog(val currentLatLng: LatLng) : MapAlertDialog
    data object None : MapAlertDialog
}