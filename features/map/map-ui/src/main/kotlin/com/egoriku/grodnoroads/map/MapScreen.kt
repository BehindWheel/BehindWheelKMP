package com.egoriku.grodnoroads.map

import android.graphics.Point
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.extensions.toast
import com.egoriku.grodnoroads.foundation.KeepScreenOn
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.map.camera.CameraInfo
import com.egoriku.grodnoroads.map.dialog.IncidentDialog
import com.egoriku.grodnoroads.map.dialog.MarkerInfoBottomSheet
import com.egoriku.grodnoroads.map.dialog.ReportDialog
import com.egoriku.grodnoroads.map.domain.component.MapComponent
import com.egoriku.grodnoroads.map.domain.component.MapComponent.ReportDialogFlow
import com.egoriku.grodnoroads.map.domain.model.*
import com.egoriku.grodnoroads.map.domain.model.MapAlertDialog.*
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.*
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore.Label
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore.Label.ShowToast
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStore.Intent.ReportAction
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.map.foundation.LogoProgressIndicator
import com.egoriku.grodnoroads.map.foundation.ModalBottomSheet
import com.egoriku.grodnoroads.map.foundation.UsersCount
import com.egoriku.grodnoroads.map.foundation.map.GoogleMapComponent
import com.egoriku.grodnoroads.map.markers.CameraMarker
import com.egoriku.grodnoroads.map.markers.ReportsMarker
import com.egoriku.grodnoroads.map.mode.DefaultOverlay
import com.egoriku.grodnoroads.map.mode.chooselocation.ChooseLocation
import com.egoriku.grodnoroads.map.mode.default.DefaultMode
import com.egoriku.grodnoroads.map.mode.drive.DriveMode
import com.egoriku.grodnoroads.map.util.MarkerCache
import com.egoriku.grodnoroads.map.util.SoundUtil
import com.egoriku.grodnoroads.resources.R
import com.google.android.gms.maps.Projection
import kotlinx.collections.immutable.persistentListOf
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(component: MapComponent) {
    val markerCache = koinInject<MarkerCache>()
    val soundUtil = koinInject<SoundUtil>()

    Surface {
        var cameraInfo by rememberMutableState<MapEvent.Camera?> { null }

        val alerts by component.alerts.collectAsState(initial = persistentListOf())
        val appMode by component.appMode.collectAsState(AppMode.Default)
        val location by component.lastLocation.collectAsState(LastLocation.None)
        val mapConfig by component.mapConfig.collectAsState(initial = MapConfig.EMPTY)
        val mapEvents by component.mapEvents.collectAsState(initial = persistentListOf())
        val mapAlertDialog by component.mapAlertDialog.collectAsState(initial = None)
        val userCount by component.userCount.collectAsState(initial = 0)
        val speedLimit by component.speedLimit.collectAsState(initial = -1)
        val quickActionsState by component.quickActionsState.collectAsState(initial = QuickActionsState())

        AlertDialogs(
            mapAlertDialog = mapAlertDialog,
            onClose = component::closeDialog,
            reportAction = component::reportAction
        )
        LabelsSubscription(component)

        var isMapLoaded by rememberMutableState { false }
        var isCameraMoving by rememberMutableState { true }
        var projection by rememberMutableState<Projection?> { null }

        GoogleMapComponent(
            appMode = appMode,
            mapConfig = mapConfig,
            lastLocation = location,
            onMapLoaded = { isMapLoaded = true },
            containsOverlay = mapAlertDialog != None,
            loading = {
                AnimatedVisibility(
                    modifier = Modifier.matchParentSize(),
                    visible = !isMapLoaded,
                    enter = EnterTransition.None,
                    exit = fadeOut()
                ) {
                    LogoProgressIndicator()
                }
            },
            onCameraMoving = {
                isCameraMoving = it
            },
            onProjection = { projection = it },
            mapZoomChangeEnabled = appMode == AppMode.ChooseLocation,
            onMapZoom = component::setUserMapZoom,
            locationChangeEnabled = appMode == AppMode.ChooseLocation,
            onLocation = component::setLocation,
            content = {
                mapEvents.forEach { mapEvent ->
                    when (mapEvent) {
                        is MapEvent.Camera -> {
                            when (mapEvent) {
                                is StationaryCamera -> CameraMarker(
                                    camera = mapEvent,
                                    provideIcon = { markerCache.getVector(id = R.drawable.ic_map_stationary_camera) },
                                    onClick = { cameraInfo = mapEvent }
                                )

                                is MediumSpeedCamera -> CameraMarker(
                                    camera = mapEvent,
                                    provideIcon = { markerCache.getVector(id = R.drawable.ic_map_medium_speed_camera) },
                                    onClick = { cameraInfo = mapEvent }
                                )

                                is MobileCamera -> CameraMarker(
                                    camera = mapEvent,
                                    provideIcon = { markerCache.getVector(id = R.drawable.ic_map_mobile_camera) },
                                    onClick = { cameraInfo = mapEvent }
                                )
                            }
                        }

                        is MapEvent.Reports -> ReportsMarker(
                            reports = mapEvent,
                            onMarkerClick = component::showMarkerInfoDialog
                        )
                    }
                }
            }
        )

        if (isMapLoaded) {
            AlwaysKeepScreenOn(mapConfig.keepScreenOn)
            Box(modifier = Modifier.fillMaxSize()) {
                AnimatedContent(targetState = appMode, label = "app mode") { state ->
                    when (state) {
                        AppMode.Default -> {
                            DefaultMode(
                                onLocationEnabled = component::startLocationUpdates,
                                onLocationDisabled = component::onLocationDisabled,
                                report = component::openChooseLocation
                            )
                        }

                        AppMode.Drive -> {
                            DriveMode(
                                stopDrive = component::stopLocationUpdates,
                                reportPolice = {
                                    if (location != LastLocation.None) {
                                        component.openReportFlow(
                                            reportDialogFlow = ReportDialogFlow.TrafficPolice(
                                                location.latLng
                                            )
                                        )
                                    }
                                },
                                reportIncident = {
                                    if (location != LastLocation.None) {
                                        component.openReportFlow(
                                            reportDialogFlow = ReportDialogFlow.RoadIncident(
                                                location.latLng
                                            )
                                        )
                                    }
                                }
                            )
                        }

                        AppMode.ChooseLocation -> {
                            ChooseLocation(
                                isCameraMoving = isCameraMoving,
                                onCancel = component::cancelChooseLocationFlow,
                                onLocationSelected = {
                                    val latLng = projection?.fromScreenLocation(
                                        Point(
                                            /* x = */ it.x.toInt(),
                                            /* y = */ it.y.toInt()
                                        )
                                    ) ?: return@ChooseLocation
                                    component.reportChooseLocation(latLng)
                                }
                            )
                        }
                    }
                }
                UsersCount(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 4.dp, end = 8.dp),
                    count = userCount
                )
                DefaultOverlay(
                    isDriveMode = appMode == AppMode.Drive,
                    currentSpeed = location.speed,
                    speedLimit = speedLimit,
                    quickActionsState = quickActionsState,
                    alerts = alerts,
                    onPreferenceChange = component::updatePreferences,
                    onOverSpeed = soundUtil::playOverSpeedSoundSound
                )
            }
        }

        ModalBottomSheet(
            data = cameraInfo,
            onDismissRequest = { cameraInfo = null },
        ) {
            CameraInfo(it)
        }
    }
}

@Composable
private fun AlwaysKeepScreenOn(enabled: Boolean) {
    KeepScreenOn(enabled)
}

@Composable
private fun AlertDialogs(
    mapAlertDialog: MapAlertDialog,
    onClose: () -> Unit,
    reportAction: (ReportAction.Params) -> Unit
) {
    when (val state = mapAlertDialog) {
        is MarkerInfoDialog -> {
            MarkerInfoBottomSheet(
                reports = state.reports,
                onClose = onClose
            )
        }

        is PoliceDialog -> {
            ReportDialog(
                onClose = onClose,
                onSend = { mapEvent, shortMessage, message ->
                    reportAction(
                        ReportAction.Params(
                            latLng = state.currentLatLng,
                            mapEventType = mapEvent,
                            shortMessage = shortMessage,
                            message = message
                        )
                    )
                }
            )
        }

        is RoadIncidentDialog -> {
            IncidentDialog(
                onClose = onClose,
                onSend = { mapEvent, shortMessage, message ->
                    reportAction(
                        ReportAction.Params(
                            latLng = state.currentLatLng,
                            mapEventType = mapEvent,
                            shortMessage = shortMessage,
                            message = message
                        )
                    )
                }
            )
        }

        is None -> Unit
    }
}

@Composable
private fun LabelsSubscription(component: MapComponent) {
    val context = LocalContext.current
    val labels by component.labels.collectAsState(initial = Label.None)

    LaunchedEffect(labels) {
        when (val label = labels) {
            is ShowToast -> context.toast(label.resId)
            else -> {}
        }
    }
}