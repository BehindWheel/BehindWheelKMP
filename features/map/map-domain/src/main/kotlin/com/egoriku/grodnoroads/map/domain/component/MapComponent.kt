package com.egoriku.grodnoroads.map.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.eventreporting.domain.model.ReportingResult
import com.egoriku.grodnoroads.map.domain.model.*
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.quicksettings.domain.component.QuickSettingsComponent
import com.google.android.gms.maps.model.LatLng
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

@Stable
interface MapComponent {

    val quickSettingsComponent: QuickSettingsComponent
    val notificationEvents: SharedFlow<Notification>

    val lastLocation: Flow<LastLocation>
    val initialLocation: Flow<LatLng>

    val appMode: Flow<AppMode>
    val mapBottomSheet: Flow<MapBottomSheet>
    val mapConfig: Flow<MapConfig>
    val mapEvents: Flow<ImmutableList<MapEvent>>
    val userCount: Flow<Int>

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
    fun showMarkerInfoDialog(reports: Reports)
    fun openQuickSettings()
    fun closeDialog()
}