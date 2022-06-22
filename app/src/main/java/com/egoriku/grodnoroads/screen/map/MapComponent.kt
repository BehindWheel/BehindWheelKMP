package com.egoriku.grodnoroads.screen.map

import com.egoriku.grodnoroads.screen.map.domain.*
import com.egoriku.grodnoroads.screen.map.domain.MapEvent.Reports
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface MapComponent {

    val mapAlertDialog: Flow<MapAlertDialog>
    val appMode: Flow<AppMode>
    val location: Flow<LocationState>
    val mapEvents: Flow<List<MapEvent>>
    val mapPreferences: Flow<GrodnoRoadsMapPreferences>

    val labels: Flow<Label>

    val alerts: Flow<List<Alert>>

    fun openReportFlow(reportDialogFlow: ReportDialogFlow)

    fun reportAction(
        latLng: LatLng,
        type: MapEventType,
        shortMessage: String,
        message: String
    )

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