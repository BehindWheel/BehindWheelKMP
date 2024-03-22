@file:Suppress("NAME_SHADOWING")

package com.egoriku.grodnoroads.map

import android.graphics.Point
import androidx.compose.animation.AnimatedContent
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
import com.egoriku.grodnoroads.compose.snackbar.SnackbarHost
import com.egoriku.grodnoroads.compose.snackbar.model.Icon
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.ActionMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarState
import com.egoriku.grodnoroads.extensions.reLaunch
import com.egoriku.grodnoroads.foundation.core.alignment.OffsetAlignment
import com.egoriku.grodnoroads.foundation.core.animation.FadeInOutAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.rememberMutableFloatState
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.map.appupdate.InAppUpdateHandle
import com.egoriku.grodnoroads.map.camera.CameraInfo
import com.egoriku.grodnoroads.map.dialog.MarkerInfoBottomSheet
import com.egoriku.grodnoroads.map.domain.component.MapComponent
import com.egoriku.grodnoroads.map.domain.model.*
import com.egoriku.grodnoroads.map.domain.model.AppMode.*
import com.egoriku.grodnoroads.map.domain.model.LastLocation.Companion.UNKNOWN_LOCATION
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.*
import com.egoriku.grodnoroads.map.foundation.ModalBottomSheet
import com.egoriku.grodnoroads.map.foundation.UsersCount
import com.egoriku.grodnoroads.map.google.MarkerSize.Large
import com.egoriku.grodnoroads.map.google.MarkerSize.Small
import com.egoriku.grodnoroads.map.google.markers.CameraMarker
import com.egoriku.grodnoroads.map.google.markers.NavigationMarker
import com.egoriku.grodnoroads.map.google.markers.ReportsMarker
import com.egoriku.grodnoroads.map.google.ui.MapOverlayActions
import com.egoriku.grodnoroads.map.google.util.rememberMapProperties
import com.egoriku.grodnoroads.map.mode.DefaultOverlay
import com.egoriku.grodnoroads.map.mode.chooselocation.ChooseLocation
import com.egoriku.grodnoroads.map.mode.default.DefaultMode
import com.egoriku.grodnoroads.map.mode.drive.DriveMode
import com.egoriku.grodnoroads.map.ui.KeepScreenOn
import com.egoriku.grodnoroads.map.util.MarkerCache
import com.egoriku.grodnoroads.map.util.SnackbarMessageBuilder
import com.egoriku.grodnoroads.maps.compose.GoogleMap
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.api.CameraMoveState
import com.egoriku.grodnoroads.maps.compose.api.ZoomLevelState
import com.egoriku.grodnoroads.maps.compose.impl.onMapScope
import com.egoriku.grodnoroads.quicksettings.QuickSettingsBottomSheet
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.shared.core.models.MapEventType.*
import com.google.android.gms.maps.Projection
import com.google.maps.android.ktx.model.cameraPosition
import com.google.maps.android.ui.IconGenerator
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import com.egoriku.grodnoroads.resources.R as R_res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    contentPadding: PaddingValues,
    component: MapComponent,
    onBottomNavigationVisibilityChange: (Boolean) -> Unit
) {
    val context = LocalContext.current

    val snackbarMessageBuilder = remember { SnackbarMessageBuilder(context) }
    val snackbarState = remember { SnackbarState() }

    val markerCache = koinInject<MarkerCache>()

    LaunchedEffect(Unit) {
        component
            .notificationEvents
            .onEach {
                when (it) {
                    Notification.RepostingSuccess ->
                        snackbarState.show(
                            SnackbarMessage.SimpleMessage(
                                title = MessageData.Resource(R.string.reporting_notification_sent),
                                icon = Icon.Res(id = R.drawable.ic_check_circle)
                            )
                        )
                }
            }
            .launchIn(this)
    }

    Surface {
        var cameraInfo by rememberMutableState<MapEvent.Camera?> { null }

        val alerts by component.alerts.collectAsState(initial = persistentListOf())
        val appMode by component.appMode.collectAsState(Default)

        val location by component.lastLocation.collectAsState(LastLocation.None)
        val initialLocation by component.initialLocation.collectAsState(UNKNOWN_LOCATION)

        val mapConfig by component.mapConfig.collectAsState(initial = MapConfig.EMPTY)
        val mapEvents by component.mapEvents.collectAsState(initial = persistentListOf())
        val mapBottomSheet by component.mapBottomSheet.collectAsState(initial = MapBottomSheet.None)
        val userCount by component.userCount.collectAsState(initial = 0)
        val speedLimit by component.speedLimit.collectAsState(initial = -1)

        when (val state = mapBottomSheet) {
            is MapBottomSheet.MarkerInfo -> {
                MarkerInfoBottomSheet(
                    reports = state.reports,
                    onClose = component::closeDialog
                )
            }
            is MapBottomSheet.QuickSettings -> {
                QuickSettingsBottomSheet(
                    component = component.quickSettingsComponent,
                    onClose = component::closeDialog
                )
            }
            is MapBottomSheet.None -> Unit
        }

        val coroutineScope = rememberCoroutineScope()
        val iconGenerator = remember { IconGenerator(context) }
        var cameraUpdatesJob by rememberMutableState<Job?> { null }

        var isMapLoaded by rememberMutableState { false }
        var isCameraMoving by rememberMutableState { false }
        var isCameraUpdatesEnabled by rememberMutableState { true }
        var idleZoomLevel by rememberMutableFloatState { -1f }

        var isRequestCurrentLocation by rememberMutableState { false }

        var cameraMoveState = remember<CameraMoveState> { CameraMoveState.Idle }

        val overlayVisible by remember {
            derivedStateOf { !isCameraUpdatesEnabled || appMode != Drive }
        }

        val markerSize by remember {
            derivedStateOf {
                when {
                    idleZoomLevel == -1f -> Large
                    idleZoomLevel <= 9f -> Small
                    else -> Large
                }
            }
        }

        var projection by rememberMutableState<Projection?> { null }
        var mapUpdater by rememberMutableState<MapUpdater?> { null }

        if (mapConfig != MapConfig.EMPTY && initialLocation != UNKNOWN_LOCATION) {
            val mapProperties = rememberMapProperties(
                locationAvailable = {
                    location.latLng != UNKNOWN_LOCATION
                },
                mapConfig = mapConfig,
                appMode = appMode,
                isRequestCurrentLocation = isRequestCurrentLocation
            )
            GoogleMap(
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentPadding = contentPadding,
                mapProperties = mapProperties,
                onMapLoaded = { map ->
                    projection = map.projection
                    isMapLoaded = true
                },
                cameraPositionProvider = {
                    cameraPosition {
                        target(initialLocation)
                        zoom(mapConfig.zoomLevel)
                    }
                },
                onMapUpdaterChanged = { mapUpdater = it },
                onProjectionChanged = {
                    if (appMode == ChooseLocation) {
                        projection = it
                    }
                },
                onZoomChanged = { zoomLevel ->
                    when (zoomLevel) {
                        is ZoomLevelState.Idle -> {
                            val zoom = zoomLevel.zoom

                            idleZoomLevel = zoom
                            if (appMode == ChooseLocation) {
                                component.setUserMapZoom(zoom)
                            }
                        }
                        is ZoomLevelState.Moving -> Unit
                    }
                },
                cameraMoveStateChanged = { state ->
                    cameraMoveState = state

                    when (appMode) {
                        ChooseLocation -> {
                            isCameraMoving = cameraMoveState == CameraMoveState.UserGesture
                        }
                        Drive -> {
                            if (cameraMoveState == CameraMoveState.UserGesture) {
                                cameraUpdatesJob = coroutineScope.reLaunch(cameraUpdatesJob) {
                                    isCameraUpdatesEnabled = false
                                    delay(5000)
                                    isCameraUpdatesEnabled = true
                                }
                            }
                        }
                        else -> {
                            isCameraUpdatesEnabled = true
                        }
                    }
                }
            )

            LaunchedEffect(location, appMode) {
                val mapUpdater = mapUpdater ?: return@LaunchedEffect
                if (location == LastLocation.None) return@LaunchedEffect

                when (appMode) {
                    Drive -> {
                        if (cameraMoveState == CameraMoveState.Animating) return@LaunchedEffect

                        if (mapUpdater.isInitialCameraAnimation()) {
                            mapUpdater.animateCamera(
                                target = location.latLng,
                                zoom = mapConfig.zoomLevel,
                                bearing = location.bearing
                            )
                        }

                        if (!isCameraUpdatesEnabled || cameraInfo != null || mapBottomSheet != MapBottomSheet.None)
                            return@LaunchedEffect

                        mapUpdater.animateCamera(
                            target = location.latLng,
                            zoom = mapConfig.zoomLevel,
                            bearing = location.bearing
                        )
                    }
                    else -> {}
                }
            }

            LaunchedEffect(appMode) {
                when (appMode) {
                    Default, ChooseLocation -> {
                        mapUpdater.onMapScope {
                            resetLastLocation()
                            animateZoom(mapConfig.zoomLevel)
                        }
                    }
                    else -> {}
                }
            }

            LaunchedEffect(location, isRequestCurrentLocation) {
                if (location == LastLocation.None) return@LaunchedEffect

                if (isRequestCurrentLocation) {
                    mapUpdater.onMapScope {
                        animateTarget(
                            target = location.latLng,
                            zoom = if (appMode == Default) 14.5f else null,
                            onFinish = { isRequestCurrentLocation = false },
                            onCancel = { isRequestCurrentLocation = false }
                        )
                    }
                }
            }

            LaunchedEffect(appMode) {
                when (appMode) {
                    Default -> onBottomNavigationVisibilityChange(true)
                    Drive, ChooseLocation -> onBottomNavigationVisibilityChange(false)
                }
            }

            mapUpdater.onMapScope {
                if (appMode == Drive && location != LastLocation.None) {
                    NavigationMarker(
                        appMode = appMode,
                        position = location.latLng,
                        bearing = location.bearing,
                        icon = { markerCache.getIcon(id = R.drawable.ic_navigation_arrow) },
                        rotation = location.bearing
                    )
                }

                mapEvents.forEach { mapEvent ->
                    when (mapEvent) {
                        is MapEvent.Camera -> {
                            when (mapEvent) {
                                is StationaryCamera -> CameraMarker(
                                    position = mapEvent.position,
                                    markerSize = markerSize,
                                    icon = {
                                        val id = when (markerSize) {
                                            Large -> R_res.drawable.ic_map_stationary_camera
                                            Small -> R_res.drawable.ic_map_stationary_camera_small
                                        }
                                        markerCache.getIcon(id)
                                    },
                                    onClick = { cameraInfo = mapEvent }
                                )

                                is MediumSpeedCamera -> {
                                    CameraMarker(
                                        position = mapEvent.position,
                                        markerSize = markerSize,
                                        icon = {
                                            val id = when (markerSize) {
                                                Large -> R_res.drawable.ic_map_medium_speed_camera
                                                Small -> R_res.drawable.ic_map_medium_speed_camera_small
                                            }
                                            markerCache.getIcon(id)
                                        },
                                        onClick = { cameraInfo = mapEvent }
                                    )
                                }

                                is MobileCamera -> {
                                    CameraMarker(
                                        position = mapEvent.position,
                                        markerSize = markerSize,
                                        icon = {
                                            val id = when (markerSize) {
                                                Large -> R_res.drawable.ic_map_mobile_camera
                                                Small -> R_res.drawable.ic_map_mobile_camera_small
                                            }
                                            markerCache.getIcon(id)
                                        },
                                        onClick = { cameraInfo = mapEvent }
                                    )
                                }
                            }
                        }

                        is MapEvent.Reports -> {
                            ReportsMarker(
                                position = mapEvent.position,
                                markerSize = markerSize,
                                message = mapEvent.markerMessage,
                                iconProvider = {
                                    when (mapEvent.mapEventType) {
                                        TrafficPolice -> markerCache.getIcon(R.drawable.ic_map_police)
                                        RoadIncident -> markerCache.getIcon(R.drawable.ic_map_road_incident)
                                        CarCrash -> markerCache.getIcon(R.drawable.ic_map_car_crash)
                                        TrafficJam -> markerCache.getIcon(R.drawable.ic_map_traffic_jam)
                                        WildAnimals -> markerCache.getIcon(R.drawable.ic_map_wild_animals)
                                        Unsupported -> null
                                    }
                                },
                                iconGenerator = { iconGenerator },
                                onClick = { component.showMarkerInfoDialog(mapEvent) }
                            )
                        }
                    }
                }
            }
        }

        FadeInOutAnimatedVisibility(visible = isMapLoaded) {
            AlwaysKeepScreenOn(mapConfig.keepScreenOn)
            Box(modifier = Modifier.fillMaxSize()) {
                AnimatedContent(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(contentPadding),
                    targetState = appMode,
                    label = "app_mode"
                ) { state ->
                    when (state) {
                        Default -> {
                            DefaultMode(
                                onLocationRequestStateChanged = {
                                    val message = snackbarMessageBuilder.handleDriveModeRequest(it)

                                    if (message == null) {
                                        component.startDriveMode()
                                    } else {
                                        coroutineScope.launch {
                                            snackbarState.show(message)
                                        }
                                    }
                                },
                                openReportFlow = component::switchToChooseLocationFlow
                            )
                        }

                        Drive -> {
                            DriveMode(
                                back = component::stopDriveMode,
                                openChooseLocation = component::switchToChooseLocationFlow
                            )
                        }

                        ChooseLocation -> {
                            ChooseLocation(
                                isCameraMoving = isCameraMoving,
                                isChooseInDriveMode = mapConfig.isChooseInDriveMode,
                                onCancel = component::cancelChooseLocationFlow,
                                onLocationSelected = { offset ->
                                    val latLng = projection?.fromScreenLocation(
                                        Point(
                                            /* x = */ offset.x.toInt(),
                                            /* y = */ offset.y.toInt()
                                        )
                                    ) ?: return@ChooseLocation
                                    component.startReporting(latLng)
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
                FadeInOutAnimatedVisibility(
                    modifier = Modifier
                        .padding(contentPadding)
                        .align(OffsetAlignment(xOffset = 1f, yOffset = 0.45f)),
                    visible = overlayVisible,
                ) {
                    MapOverlayActions(
                        modifier = Modifier.padding(end = 16.dp),
                        zoomIn = { mapUpdater?.zoomIn() },
                        zoomOut = { mapUpdater?.zoomOut() },
                        onLocationRequestStateChanged = {
                            if (appMode == Drive) {
                                mapUpdater.onMapScope {
                                    animateCurrentLocation(
                                        target = location.latLng,
                                        zoom = mapConfig.zoomLevel,
                                        bearing = location.bearing
                                    )
                                }
                            } else {
                                val message =
                                    snackbarMessageBuilder.handleCurrentLocationRequest(it)

                                if (message == null) {
                                    isRequestCurrentLocation = true
                                    component.requestCurrentLocation()
                                } else {
                                    coroutineScope.launch {
                                        snackbarState.show(message)
                                    }
                                }
                            }
                        },
                    )
                }
                DefaultOverlay(
                    contentPadding = contentPadding,
                    isOverlayVisible = overlayVisible,
                    isDriveMode = appMode == Drive,
                    currentSpeed = location.speed,
                    speedLimit = speedLimit,
                    alerts = alerts,
                    onOpenQuickSettings = component::openQuickSettings
                )
                SnackbarHost(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(contentPadding),
                    hostState = snackbarState,
                    paddingValues = PaddingValues(16.dp)
                )
            }
        }

        ModalBottomSheet(
            data = cameraInfo,
            onDismissRequest = { cameraInfo = null },
            content = { CameraInfo(it) },
        )
        InAppUpdateHandle(
            onDownloaded = {
                coroutineScope.launch {
                    snackbarState.show(
                        ActionMessage(
                            title = MessageData.Resource(R.string.snackbar_in_app_update_install),
                            onAction = it
                        )
                    )
                }
            }
        )
    }
}

@Composable
private fun AlwaysKeepScreenOn(enabled: Boolean) {
    KeepScreenOn(enabled)
}
