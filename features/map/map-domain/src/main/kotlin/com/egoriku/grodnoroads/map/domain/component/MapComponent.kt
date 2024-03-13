package com.egoriku.grodnoroads.map.domain.component

import com.egoriku.grodnoroads.eventreporting.domain.model.ReportingResult
import com.egoriku.grodnoroads.map.domain.model.*
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsPref
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsState
import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface MapComponent {

    val notificationEvents: SharedFlow<Notification>

    val lastLocation: Flow<LastLocation>
    val initialLocation: Flow<LatLng>

    val appMode: Flow<AppMode>
    val mapAlertDialog: Flow<MapAlertDialog>
    val mapConfig: Flow<MapConfig>
    val mapEvents: Flow<ImmutableList<MapEvent>>
    val userCount: Flow<Int>
    val quickActionsState: Flow<QuickActionsState>

    val alerts: Flow<ImmutableList<Alert>>

    val speedLimit: Flow<Int>

    fun processReporting(result: ReportingResult)

    fun setLocation(latLng: LatLng)

    fun switchToChooseLocationFlow()
    fun startReporting(latLng: LatLng)
    fun setUserMapZoom(zoom: Float)
    fun cancelChooseLocationFlow()

    fun startDriveMode()
    fun stopDriveMode()
    fun requestCurrentLocation()

    fun updatePreferences(pref: QuickActionsPref)

    fun showMarkerInfoDialog(reports: Reports)
    fun closeDialog()
}