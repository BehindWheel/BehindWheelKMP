package com.egoriku.grodnoroads.screen.map.domain.component

import com.egoriku.grodnoroads.screen.map.domain.model.Alert
import com.egoriku.grodnoroads.screen.map.domain.model.AppMode
import com.egoriku.grodnoroads.screen.map.domain.model.LocationState
import com.egoriku.grodnoroads.screen.map.domain.model.MapAlertDialog
import com.egoriku.grodnoroads.screen.map.domain.model.MapEvent
import com.egoriku.grodnoroads.screen.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.screen.map.domain.store.LocationStoreFactory.Label
import com.egoriku.grodnoroads.screen.map.domain.store.MapEventsStoreFactory.Intent.ReportAction
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface MapComponent {

    val mapAlertDialog: Flow<MapAlertDialog>
    val appMode: Flow<AppMode>
    val location: Flow<LocationState>
    val mapEvents: Flow<List<MapEvent>>

    val labels: Flow<Label>

    val alerts: Flow<List<Alert>>

    fun openReportFlow(reportDialogFlow: ReportDialogFlow)
    fun reportAction(params: ReportAction.Params)

    fun startLocationUpdates()
    fun stopLocationUpdates()

    fun onLocationDisabled()

    fun showMarkerInfoDialog(reports: Reports)
    fun closeDialog()

    sealed interface ReportDialogFlow {
        data class TrafficPolice(val latLng: LatLng) : ReportDialogFlow
        data class RoadIncident(val latLng: LatLng) : ReportDialogFlow
    }
}