package com.egoriku.grodnoroads.map.domain.model

import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.mapswrapper.core.StableLatLng

sealed interface MapAlertDialog {

    data class MarkerInfoDialog(val reports: Reports) : MapAlertDialog
    data class PoliceDialog(val currentLatLng: StableLatLng) : MapAlertDialog
    data class RoadIncidentDialog(val currentLatLng: StableLatLng) : MapAlertDialog
    data object None : MapAlertDialog
}