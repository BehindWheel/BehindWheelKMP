@file:Suppress("NAME_SHADOWING")

package com.egoriku.grodnoroads.guidance.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.reporting_notification_sent
import com.egoriku.grodnoroads.compose.resources.snackbar_in_app_update_install
import com.egoriku.grodnoroads.compose.snackbar.SnackbarHost
import com.egoriku.grodnoroads.compose.snackbar.model.Icon
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.ActionMessage
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarState
import com.egoriku.grodnoroads.extensions.coroutines.reLaunch
import com.egoriku.grodnoroads.extensions.decompose.onChild
import com.egoriku.grodnoroads.foundation.core.alignment.OffsetAlignment
import com.egoriku.grodnoroads.foundation.core.animation.FadeInOutAnimatedVisibility
import com.egoriku.grodnoroads.foundation.core.rememberMutableFloatState
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.CheckCircle
import com.egoriku.grodnoroads.guidance.domain.component.GuidanceComponent
import com.egoriku.grodnoroads.guidance.domain.model.AppMode
import com.egoriku.grodnoroads.guidance.domain.model.LastLocation
import com.egoriku.grodnoroads.guidance.domain.model.LastLocation.Companion.UNKNOWN_LOCATION
import com.egoriku.grodnoroads.guidance.domain.model.MapBottomSheet
import com.egoriku.grodnoroads.guidance.domain.model.MapConfig
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.MediumSpeedCamera
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.MobileCamera
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.StationaryCamera
import com.egoriku.grodnoroads.guidance.domain.model.MapEvents
import com.egoriku.grodnoroads.guidance.domain.model.event.AlertEvent
import com.egoriku.grodnoroads.guidance.domain.model.event.Notification
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.CarCrash
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.MediumSpeed
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.MediumSpeedSmall
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.Mobile
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.MobileSmall
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.NavigationArrow
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.Police
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.RoadIncident
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.Stationary
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.StationarySmall
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.TrafficJam
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.WildAnimals
import com.egoriku.grodnoroads.guidance.screen.sound.SoundController
import com.egoriku.grodnoroads.guidance.screen.ui.CameraInfo
import com.egoriku.grodnoroads.guidance.screen.ui.KeepScreenOn
import com.egoriku.grodnoroads.guidance.screen.ui.appupdate.InAppUpdateHandle
import com.egoriku.grodnoroads.guidance.screen.ui.dialog.MarkerInfoBottomSheet
import com.egoriku.grodnoroads.guidance.screen.ui.foundation.ModalBottomSheet
import com.egoriku.grodnoroads.guidance.screen.ui.foundation.UsersCount
import com.egoriku.grodnoroads.guidance.screen.ui.google.MapOverlayActions
import com.egoriku.grodnoroads.guidance.screen.ui.google.MarkerSize.Large
import com.egoriku.grodnoroads.guidance.screen.ui.google.MarkerSize.Small
import com.egoriku.grodnoroads.guidance.screen.ui.google.marker.CameraMarker
import com.egoriku.grodnoroads.guidance.screen.ui.google.marker.NavigationMarker
import com.egoriku.grodnoroads.guidance.screen.ui.google.marker.ReportsMarker
import com.egoriku.grodnoroads.guidance.screen.ui.google.rememberMapProperties
import com.egoriku.grodnoroads.guidance.screen.ui.google.rememberMarkerGenerator
import com.egoriku.grodnoroads.guidance.screen.ui.mode.DefaultOverlay
import com.egoriku.grodnoroads.guidance.screen.ui.mode.chooselocation.ChooseLocation
import com.egoriku.grodnoroads.guidance.screen.ui.mode.default.DefaultMode
import com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.DriveMode
import com.egoriku.grodnoroads.guidance.screen.util.rememberSnackbarMessageBuilder
import com.egoriku.grodnoroads.maps.compose.GoogleMap
import com.egoriku.grodnoroads.maps.compose.api.CameraMoveState
import com.egoriku.grodnoroads.maps.compose.api.ZoomLevelState
import com.egoriku.grodnoroads.maps.compose.core.CameraPosition
import com.egoriku.grodnoroads.maps.compose.core.Point
import com.egoriku.grodnoroads.maps.compose.core.Projection
import com.egoriku.grodnoroads.maps.compose.core.toScreenLatLng
import com.egoriku.grodnoroads.maps.compose.extension.projection
import com.egoriku.grodnoroads.maps.compose.impl.onMapScope
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater
import com.egoriku.grodnoroads.quicksettings.screen.QuickSettingsBottomSheet
import com.egoriku.grodnoroads.shared.models.MapEventType
import com.egoriku.grodnoroads.specialevent.screen.SpecialEventDialog
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidanceScreen(
    contentPadding: PaddingValues,
    component: GuidanceComponent,
    modifier: Modifier = Modifier,
    onBottomNavigationVisibilityChange: (Boolean) -> Unit
) {
    val updatedBottomNavigationVisibility by rememberUpdatedState(onBottomNavigationVisibilityChange)

    val snackbarMessageBuilder = rememberSnackbarMessageBuilder()
    val snackbarState = remember { SnackbarState() }

    val markerCache = koinInject<MarkerCache>()
    val soundController = koinInject<SoundController>()

    val iconGenerator = rememberMarkerGenerator()

    val specialEventSlot by component.specialEventComponent.specialEvents.collectAsState()
    specialEventSlot.onChild { dialogComponent ->
        SpecialEventDialog(
            eventType = dialogComponent.eventType,
            onClose = dialogComponent::dismiss
        )
    }

    LaunchedEffect(Unit) {
        component
            .notificationEvents
            .onEach {
                when (it) {
                    Notification.RepostingSuccess ->
                        snackbarState.show(
                            SnackbarMessage.SimpleMessage(
                                title = MessageData.StringRes(Res.string.reporting_notification_sent),
                                icon = Icon.DrawableRes(imageVector = GrodnoRoads.Outlined.CheckCircle)
                            )
                        )
                }
            }
            .launchIn(this)

        component.alertEvents
            .onEach {
                when (it) {
                    is AlertEvent.CameraLimit -> {
                        soundController.playCameraLimit(
                            id = it.id,
                            speedLimit = it.speedLimit,
                            cameraType = it.cameraType
                        )
                    }
                    is AlertEvent.IncidentAlert -> {
                        soundController.playIncident(
                            id = it.id,
                            mapEventType = it.mapEventType
                        )
                    }
                    is AlertEvent.OverSpeed -> soundController.playOverSpeed()
                    is AlertEvent.VolumeChange -> {
                        soundController.setVolume(level = it.volume)
                        soundController.setLoudness(loudness = it.loudness)
                    }
                }
            }
            .launchIn(this)
    }

    Surface(modifier = modifier) {
        var cameraInfo by rememberMutableState<MapEvent.Camera?> { null }

        val alerts by component.alerts.collectAsState(initial = persistentListOf())
        val appMode by component.appMode.collectAsState(AppMode.Default)

        val location by component.lastLocation.collectAsState(LastLocation.None)
        val initialLocation by component.initialLocation.collectAsState(UNKNOWN_LOCATION)

        val mapConfig by component.mapConfig.collectAsState(initial = MapConfig.EMPTY)
        val mapEvents by component.mapEvents.collectAsState(initial = MapEvents())
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
        var cameraUpdatesJob by rememberMutableState<Job?> { null }

        var isMapLoaded by rememberMutableState { false }
        var isCameraMoving by rememberMutableState { false }
        var isCameraUpdatesEnabled by rememberMutableState { true }
        var idleZoomLevel by rememberMutableFloatState { -1f }

        var isRequestCurrentLocation by rememberMutableState { false }

        var cameraMoveState = remember<CameraMoveState> { CameraMoveState.Idle }

        val overlayVisible by remember {
            derivedStateOf { !isCameraUpdatesEnabled || appMode != AppMode.Drive }
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
                mapConfig = mapConfig,
                appMode = appMode,
                isRequestCurrentLocation = isRequestCurrentLocation
            )
            GoogleMap(
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentPadding = contentPadding,
                mapProperties = mapProperties,
                onMapLoad = { map ->
                    projection = map.projection
                    isMapLoaded = true
                },
                cameraPositionProvider = {
                    CameraPosition(
                        target = initialLocation,
                        zoom = mapConfig.zoomLevel
                    )
                },
                onMapUpdaterChange = { mapUpdater = it },
                onProjectionChange = {
                    if (appMode == AppMode.ChooseLocation) {
                        projection = it
                    }
                },
                onZoomChange = { zoomLevel ->
                    when (zoomLevel) {
                        is ZoomLevelState.Idle -> {
                            val zoom = zoomLevel.zoom

                            idleZoomLevel = zoom
                            if (appMode == AppMode.ChooseLocation) {
                                component.setUserMapZoom(zoom)
                            }
                        }
                        is ZoomLevelState.Moving -> Unit
                    }
                },
                cameraMoveStateChange = { state ->
                    cameraMoveState = state

                    when (appMode) {
                        AppMode.ChooseLocation -> {
                            isCameraMoving = cameraMoveState == CameraMoveState.UserGesture
                        }
                        AppMode.Drive -> {
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
                    AppMode.Drive -> {
                        if (cameraMoveState == CameraMoveState.Animating) return@LaunchedEffect

                        if (mapUpdater.isInitialCameraAnimation()) {
                            mapUpdater.animateCamera(
                                target = location.latLng,
                                zoom = mapConfig.zoomLevel,
                                bearing = location.bearing
                            )
                        }

                        if (!isCameraUpdatesEnabled || cameraInfo != null || mapBottomSheet != MapBottomSheet.None) {
                            return@LaunchedEffect
                        }

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
                    AppMode.Default, AppMode.ChooseLocation -> {
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
                            zoom = if (appMode == AppMode.Default) 14.5f else null,
                            onFinish = { isRequestCurrentLocation = false },
                            onCancel = { isRequestCurrentLocation = false }
                        )
                    }
                }
            }

            LaunchedEffect(appMode) {
                when (appMode) {
                    AppMode.Default -> updatedBottomNavigationVisibility(true)
                    AppMode.Drive, AppMode.ChooseLocation -> updatedBottomNavigationVisibility(
                        false
                    )
                }
            }
            mapUpdater.onMapScope {
                if (appMode == AppMode.Drive && location != LastLocation.None) {
                    NavigationMarker(
                        appMode = appMode,
                        position = location.latLng,
                        bearing = location.bearing,
                        icon = { markerCache.getOrPut(NavigationArrow) },
                        rotation = location.bearing
                    )
                }

                mapEvents.data.forEach { mapEvent ->
                    when (mapEvent) {
                        is MapEvent.Camera -> {
                            when (mapEvent) {
                                is StationaryCamera -> CameraMarker(
                                    position = mapEvent.position,
                                    markerSize = markerSize,
                                    icon = {
                                        markerCache.getOrPut(
                                            availableMarkers = when (markerSize) {
                                                Large -> Stationary
                                                Small -> StationarySmall
                                            }
                                        )
                                    },
                                    onClick = { cameraInfo = mapEvent }
                                )

                                is MediumSpeedCamera -> {
                                    CameraMarker(
                                        position = mapEvent.position,
                                        markerSize = markerSize,
                                        icon = {
                                            markerCache.getOrPut(
                                                availableMarkers = when (markerSize) {
                                                    Large -> MediumSpeed
                                                    Small -> MediumSpeedSmall
                                                }
                                            )
                                        },
                                        onClick = { cameraInfo = mapEvent }
                                    )
                                }

                                is MobileCamera -> {
                                    CameraMarker(
                                        zIndex = 2f,
                                        position = mapEvent.position,
                                        markerSize = markerSize,
                                        icon = {
                                            markerCache.getOrPut(
                                                availableMarkers = when (markerSize) {
                                                    Large -> Mobile
                                                    Small -> MobileSmall
                                                }
                                            )
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
                                        MapEventType.TrafficPolice -> markerCache.getOrPut(Police)
                                        MapEventType.RoadIncident -> markerCache.getOrPut(
                                            RoadIncident
                                        )
                                        MapEventType.CarCrash -> markerCache.getOrPut(CarCrash)
                                        MapEventType.TrafficJam -> markerCache.getOrPut(TrafficJam)
                                        MapEventType.WildAnimals -> markerCache.getOrPut(WildAnimals)
                                        MapEventType.Unsupported -> null
                                    }
                                },
                                markerGenerator = { iconGenerator },
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
                        AppMode.Default -> {
                            DefaultMode(
                                onLocationRequestStateChange = {
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

                        AppMode.Drive -> {
                            DriveMode(
                                back = component::stopDriveMode,
                                openChooseLocation = component::switchToChooseLocationFlow
                            )
                        }

                        AppMode.ChooseLocation -> {
                            ChooseLocation(
                                isCameraMoving = isCameraMoving,
                                isChooseInDriveMode = mapConfig.isChooseInDriveMode,
                                onCancel = component::cancelChooseLocationFlow,
                                onLocationSelect = { offset ->
                                    val latLng = projection?.toScreenLatLng(
                                        Point(
                                            x = offset.x,
                                            y = offset.y
                                        )
                                    ) ?: return@ChooseLocation
                                    component.startReporting(latLng)
                                }
                            )
                        }
                    }
                }
                UsersCount(
                    count = userCount,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 4.dp, end = 16.dp)
                        .padding(contentPadding)
                )
                FadeInOutAnimatedVisibility(
                    modifier = Modifier
                        .padding(contentPadding)
                        .align(OffsetAlignment(xOffset = 1f, yOffset = 0.45f)),
                    visible = overlayVisible
                ) {
                    MapOverlayActions(
                        modifier = Modifier.padding(end = 16.dp),
                        zoomIn = { mapUpdater?.zoomIn() },
                        zoomOut = { mapUpdater?.zoomOut() },
                        onLocationRequestStateChange = {
                            if (appMode == AppMode.Drive) {
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
                        }
                    )
                }
                DefaultOverlay(
                    contentPadding = contentPadding,
                    isOverlayVisible = overlayVisible,
                    isDriveMode = appMode == AppMode.Drive,
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
            content = { CameraInfo(it) }
        )
        InAppUpdateHandle(
            onDownload = {
                coroutineScope.launch {
                    snackbarState.show(
                        ActionMessage(
                            title = MessageData.StringRes(Res.string.snackbar_in_app_update_install),
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
