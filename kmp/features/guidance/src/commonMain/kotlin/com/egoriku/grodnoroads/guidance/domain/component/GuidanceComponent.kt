package com.egoriku.grodnoroads.guidance.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.coroutines.flow.CFlow
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams
import com.egoriku.grodnoroads.guidance.domain.model.*
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.map.domain.model.Notification
import com.egoriku.grodnoroads.quicksettings.domain.component.QuickSettingsComponent
import com.egoriku.grodnoroads.specialevent.domain.component.specialevent.SpecialEventComponent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.SharedFlow

@Stable
interface GuidanceComponent {

    val quickSettingsComponent: QuickSettingsComponent
    val specialEventComponent: SpecialEventComponent
    val notificationEvents: SharedFlow<Notification>

    val lastLocation: CFlow<LastLocation>
    val initialLocation: CFlow<LatLng>

    val appMode: CFlow<AppMode>
    val mapBottomSheet: CFlow<MapBottomSheet>
    val mapConfig: CFlow<MapConfig>
    val mapEvents: CFlow<MapEvents>
    val userCount: CFlow<Int>

    val alerts: CFlow<ImmutableList<Alert>>

    val speedLimit: CFlow<Int>

    fun processReporting(params: ReportParams)

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