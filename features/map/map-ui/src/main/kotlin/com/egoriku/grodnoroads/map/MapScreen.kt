package com.egoriku.grodnoroads.map

import android.graphics.Point
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.extensions.toast
import com.egoriku.grodnoroads.foundation.KeepScreenOn
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.map.camera.CameraInfo
import com.egoriku.grodnoroads.map.dialog.IncidentDialog
import com.egoriku.grodnoroads.map.dialog.MarkerInfoBottomSheet
import com.egoriku.grodnoroads.map.dialog.ReportDialog
import com.egoriku.grodnoroads.map.domain.component.MapComponent
import com.egoriku.grodnoroads.map.domain.component.MapComponent.ReportDialogFlow
import com.egoriku.grodnoroads.map.domain.model.AppMode.*
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.model.LastLocation.Companion.UNKNOWN_LOCATION
import com.egoriku.grodnoroads.map.domain.model.MapAlertDialog
import com.egoriku.grodnoroads.map.domain.model.MapAlertDialog.*
import com.egoriku.grodnoroads.map.domain.model.MapConfig
import com.egoriku.grodnoroads.map.domain.model.MapEvent
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.*
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore.Label
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore.Label.ShowToast
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStore.Intent.ReportAction
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.map.extension.reLaunch
import com.egoriku.grodnoroads.map.foundation.ModalBottomSheet
import com.egoriku.grodnoroads.map.foundation.UsersCount
import com.egoriku.grodnoroads.map.google.markers.CameraMarker
import com.egoriku.grodnoroads.map.google.markers.NavigationMarker
import com.egoriku.grodnoroads.map.google.markers.ReportsMarker
import com.egoriku.grodnoroads.map.google.ui.MapOverlayActions
import com.egoriku.grodnoroads.map.google.util.rememberMapProperties
import com.egoriku.grodnoroads.map.mode.DefaultOverlay
import com.egoriku.grodnoroads.map.mode.chooselocation.ChooseLocation
import com.egoriku.grodnoroads.map.mode.default.DefaultMode
import com.egoriku.grodnoroads.map.mode.drive.DriveMode
import com.egoriku.grodnoroads.map.util.MarkerCache
import com.egoriku.grodnoroads.maps.compose.CameraMoveState
import com.egoriku.grodnoroads.maps.compose.GoogleMap
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.core.asStable
import com.egoriku.grodnoroads.resources.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.Projection
import com.google.android.gms.maps.model.CameraPosition
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import org.koin.compose.koinInject
import com.egoriku.grodnoroads.resources.R as R_res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    contentPadding: PaddingValues,
    component: MapComponent,
    onBottomNavigationVisibilityChange: (Boolean) -> Unit
) {
    val markerCache = koinInject<MarkerCache>()

    Surface {
        var cameraInfo by rememberMutableState<MapEvent.Camera?> { null }

        val alerts by component.alerts.collectAsState(initial = persistentListOf())
        val appMode by component.appMode.collectAsState(Default)

        val location by component.lastLocation.collectAsState(LastLocation.None)
        val initialLocation by component.initialLocation.collectAsState(UNKNOWN_LOCATION)

        val mapConfig by component.mapConfig.collectAsState(initial = MapConfig.EMPTY)
        val mapEvents by component.mapEvents.collectAsState(initial = persistentListOf())
        val mapAlertDialog by component.mapAlertDialog.collectAsState(initial = MapAlertDialog.None)
        val userCount by component.userCount.collectAsState(initial = 0)
        val speedLimit by component.speedLimit.collectAsState(initial = -1)
        val quickActionsState by component.quickActionsState.collectAsState(initial = QuickActionsState())

        AlertDialogs(
            mapAlertDialog = mapAlertDialog,
            onClose = component::closeDialog,
            reportAction = component::reportAction
        )
        LabelsSubscription(component)

        val coroutineScope = rememberCoroutineScope()
        var cameraUpdatesJob = remember<Job?> { null }

        var isMapLoaded by rememberMutableState { false }
        var isCameraMoving by rememberMutableState { false }
        var isCameraUpdatesEnabled by rememberMutableState { true }

        val overlayVisible by remember {
            derivedStateOf {
                !isCameraUpdatesEnabled || appMode != Drive
            }
        }

        var projection by rememberMutableState<Projection?> { null }

        var mapUpdater by rememberMutableState<MapUpdater?> { null }

        if (mapConfig != MapConfig.EMPTY) {
            val mapProperties = rememberMapProperties(
                latLng = location.latLng,
                mapConfig = mapConfig,
                appMode = appMode
            )
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                contentPadding = contentPadding,
                mapProperties = mapProperties,
                onMapLoaded = {
                    logD("on map loaded")
                    isMapLoaded = true
                },
                onInitialLocationTriggered = { googleMap ->
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            initialLocation.value,
                            mapConfig.zoomLevel
                        )
                    )
                    projection = googleMap.projection
                },
                onMapUpdaterChanged = {
                    logD("set mapUpdater: $it")
                    mapUpdater = it
                },
                onProjectionChanged = {
                    if (appMode == ChooseLocation) {
                        projection = it
                    }
                },
                cameraMoveStateChanged = { state ->
                    if (appMode == ChooseLocation) {
                        isCameraMoving = state == CameraMoveState.UserGesture
                    } else if (appMode == Drive) {
                        if (state == CameraMoveState.UserGesture) {
                            cameraUpdatesJob = coroutineScope.reLaunch(cameraUpdatesJob) {
                                isCameraUpdatesEnabled = false
                                delay(5000)
                                isCameraUpdatesEnabled = true
                            }
                        }
                    } else {
                        isCameraUpdatesEnabled = true
                    }
                }
            )

            LaunchedEffect(location, appMode) {
                @Suppress("NAME_SHADOWING")
                val mapUpdater = mapUpdater ?: return@LaunchedEffect
                if (location == LastLocation.None) return@LaunchedEffect
                if (!isCameraUpdatesEnabled || cameraInfo != null || mapAlertDialog != None) return@LaunchedEffect

                when (appMode) {
                    Drive -> mapUpdater.animateCamera(
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition.builder()
                                .target(location.latLng.value)
                                .bearing(location.bearing)
                                .zoom(mapConfig.zoomLevel)
                                .tilt(35.0f)
                                .build()
                        )
                    )
                    Default -> mapUpdater.animateCamera(
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition.builder()
                                .target(location.latLng.value)
                                .bearing(location.bearing)
                                .zoom(mapConfig.zoomLevel)
                                .tilt(0.0f)
                                .build()
                        )
                    )
                    ChooseLocation -> mapUpdater.animateCamera(
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition.builder()
                                .target(location.latLng.value)
                                .bearing(location.bearing)
                                .zoom(mapConfig.zoomLevel)
                                .tilt(0.0f)
                                .build()
                        )
                    )
                }
            }

            LaunchedEffect(appMode) {
                @Suppress("NAME_SHADOWING")
                val mapUpdater = mapUpdater ?: return@LaunchedEffect

                when (appMode) {
                    Default, ChooseLocation -> mapUpdater.paddingDecorator.additionalPadding(top = 0)
                    Drive -> mapUpdater.paddingDecorator.additionalPadding(top = mapUpdater.mapView.height / 3)
                }
                when (appMode) {
                    Default -> onBottomNavigationVisibilityChange(true)
                    Drive -> onBottomNavigationVisibilityChange(false)
                    ChooseLocation -> onBottomNavigationVisibilityChange(true)
                }
            }

            val scope = mapUpdater
            if (scope != null) {
                with(scope) {
                    if (appMode == Drive && location != LastLocation.None) {
                        val isLight = MaterialTheme.colorScheme.isLight

                        NavigationMarker(
                            position = location.latLng,
                            bearing = location.bearing,
                            icon = {
                                markerCache.getVector(
                                    id = if (isLight) R.drawable.ic_navigation_arrow_black else R.drawable.ic_navigation_arrow_white,
                                )
                            },
                            rotation = location.bearing
                        )
                    }
                    mapEvents.forEach { mapEvent ->
                        when (mapEvent) {
                            is MapEvent.Camera -> {
                                when (mapEvent) {
                                    is StationaryCamera -> CameraMarker(
                                        position = mapEvent.position,
                                        icon = { markerCache.getVector(id = R_res.drawable.ic_map_stationary_camera) },
                                        onClick = { cameraInfo = mapEvent }
                                    )
                                    is MediumSpeedCamera -> CameraMarker(
                                        position = mapEvent.position,
                                        icon = { markerCache.getVector(id = R_res.drawable.ic_map_medium_speed_camera) },
                                        onClick = { cameraInfo = mapEvent }
                                    )
                                    is MobileCamera -> CameraMarker(
                                        position = mapEvent.position,
                                        icon = { markerCache.getVector(id = R_res.drawable.ic_map_mobile_camera) },
                                        onClick = { cameraInfo = mapEvent }
                                    )
                                }
                            }
                            is MapEvent.Reports -> {
                                ReportsMarker(
                                    position = mapEvent.position,
                                    message = mapEvent.markerMessage,
                                    onClick = { component.showMarkerInfoDialog(mapEvent) }
                                )
                            }
                        }
                    }
                }
            }
        }

        /*Box {
            GoogleMapComponent(
                modifier = Modifier
                    .pointerInput(Unit) {
                        coroutineScope {
                            awaitEachGesture {
                                awaitFirstDown(requireUnconsumed = false)
                                isUserTouchScreen = true

                                waitForUpOrCancellation()
                                isPointerInputCanceled = true
                            }
                        }
                    },
                cameraPositionState = cameraPositionState,
                paddingValues = contentPadding,
                appMode = appMode,
                mapConfig = mapConfig,
                lastLocation = location,
                onMapLoaded = { isMapLoaded = true },
                animateCamera = animateCamera,
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
                onCameraChanges = {
                    isOverlayVisible = it
                    onBottomNavigationVisibilityChange(it)
                },
            )
        }*/

        if (isMapLoaded) {
            AlwaysKeepScreenOn(mapConfig.keepScreenOn)
            Box(modifier = Modifier.fillMaxSize()) {
                AnimatedContent(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(contentPadding),
                    targetState = appMode,
                    label = "app mode"
                ) { state ->
                    when (state) {
                        Default -> {
                            DefaultMode(
                                onLocationEnabled = component::startLocationUpdates,
                                onLocationDisabled = component::onLocationDisabled,
                                report = component::openChooseLocation
                            )
                        }

                        Drive -> {
                            DriveMode(
                                modifier = Modifier.align(Alignment.BottomCenter),
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

                        ChooseLocation -> {
                            ChooseLocation(
                                isCameraMoving = isCameraMoving,
                                onCancel = component::cancelChooseLocationFlow,
                                onLocationSelected = { offset ->
                                    val latLng = projection?.fromScreenLocation(
                                        Point(
                                            /* x = */ offset.x.toInt(),
                                            /* y = */ offset.y.toInt()
                                        )
                                    ) ?: return@ChooseLocation
                                    component.reportChooseLocation(latLng.asStable())
                                }
                            )
                        }
                    }
                }
                UsersCount(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 4.dp, end = 16.dp)
                        .padding(contentPadding),
                    count = userCount
                )
                AnimatedVisibility(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    visible = overlayVisible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    MapOverlayActions(
                        zoomIn = { mapUpdater?.zoomIn() },
                        zoomOut = { mapUpdater?.zoomOut() }
                    )
                }
                DefaultOverlay(
                    isOverlayVisible = !isCameraUpdatesEnabled,
                    isDriveMode = appMode == Drive,
                    currentSpeed = location.speed,
                    speedLimit = speedLimit,
                    quickActionsState = quickActionsState,
                    alerts = alerts,
                    onPreferenceChange = component::updatePreferences
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
    when (mapAlertDialog) {
        is MarkerInfoDialog -> {
            MarkerInfoBottomSheet(
                reports = mapAlertDialog.reports,
                onClose = onClose
            )
        }

        is PoliceDialog -> {
            ReportDialog(
                onClose = onClose,
                onSend = { mapEvent, shortMessage, message ->
                    reportAction(
                        ReportAction.Params(
                            latLng = mapAlertDialog.currentLatLng.value,
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
                            latLng = mapAlertDialog.currentLatLng.value,
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