package com.egoriku.grodnoroads.map.domain.component

import com.egoriku.grodnoroads.map.domain.model.*
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStore.Intent.ReportAction
import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface MapComponent {

    val appMode: Flow<AppMode>
    val lastLocation: Flow<LastLocation>
    val mapAlertDialog: Flow<MapAlertDialog>
    val mapConfig: Flow<MapConfig>
    val mapEvents: Flow<ImmutableList<MapEvent>>
    val userCount: Flow<Int>

    val labels: Flow<LocationStore.Label>

    val alerts: Flow<ImmutableList<Alert>>

    fun openReportFlow(reportDialogFlow: ReportDialogFlow)
    fun reportAction(params: ReportAction.Params)

    fun reportWithoutLocation()

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