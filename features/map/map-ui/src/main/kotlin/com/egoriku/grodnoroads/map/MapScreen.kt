package com.egoriku.grodnoroads.map

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.extensions.toast
import com.egoriku.grodnoroads.map.dialog.IncidentDialog
import com.egoriku.grodnoroads.map.dialog.MarkerAlertDialog
import com.egoriku.grodnoroads.map.dialog.ReportDialog
import com.egoriku.grodnoroads.map.domain.component.MapComponent
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.model.MapAlertDialog.*
import com.egoriku.grodnoroads.map.domain.model.MapConfig
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore.Label
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore.Label.ShowToast
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStore.Intent.ReportAction
import com.egoriku.grodnoroads.map.foundation.LogoProgressIndicator
import com.egoriku.grodnoroads.map.foundation.map.GoogleMapComponent
import com.egoriku.grodnoroads.map.mode.default.DefaultMode
import com.egoriku.grodnoroads.map.mode.drive.DriveMode
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MapScreen(component: MapComponent) {
    AlertDialogs(component)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        val alerts by component.alerts.collectAsState(initial = persistentListOf())
        val appMode by component.appMode.collectAsState(AppMode.Default)
        val location by component.lastLocation.collectAsState(LastLocation.None)
        val mapConfig by component.mapConfig.collectAsState(initial = MapConfig.EMPTY)
        val mapEvents by component.mapEvents.collectAsState(initial = persistentListOf())

        LabelsSubscription(component)

        Box(modifier = Modifier.fillMaxSize()) {
            val isMapLoaded = remember { mutableStateOf(false) }

            GoogleMapComponent(
                mapEvents = mapEvents,
                appMode = appMode,
                mapConfig = mapConfig,
                lastLocation = location,
                onMarkerClick = component::showMarkerInfoDialog,
                isMapLoaded = isMapLoaded
            ) {
                AnimatedVisibility(
                    modifier = Modifier.matchParentSize(),
                    visible = !isMapLoaded.value,
                    enter = EnterTransition.None,
                    exit = fadeOut()
                ) {
                    LogoProgressIndicator()
                }
            }

            if (isMapLoaded.value) {
                AnimatedContent(targetState = appMode) { state ->
                    when (state) {
                        AppMode.Default -> {
                            DefaultMode(
                                onLocationEnabled = component::startLocationUpdates,
                                onLocationDisabled = component::onLocationDisabled
                            )
                        }

                        AppMode.Drive -> {
                            DriveMode(
                                alerts = alerts,
                                lastLocation = location,
                                stopDrive = component::stopLocationUpdates,
                                reportPolice = {
                                    if (location != LastLocation.None) {
                                        component.openReportFlow(
                                            reportDialogFlow = MapComponent.ReportDialogFlow.TrafficPolice(
                                                location.latLng
                                            )
                                        )
                                    }
                                },
                                reportIncident = {
                                    if (location != LastLocation.None) {
                                        component.openReportFlow(
                                            reportDialogFlow = MapComponent.ReportDialogFlow.RoadIncident(
                                                location.latLng
                                            )
                                        )
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AlertDialogs(component: MapComponent) {
    val mapAlertDialog by component.mapAlertDialog.collectAsState(initial = None)

    when (val state = mapAlertDialog) {
        is MarkerInfoDialog -> {
            MarkerAlertDialog(
                reports = state.reports,
                onClose = {
                    component.closeDialog()
                }
            )
        }

        is PoliceDialog -> {
            ReportDialog(
                onClose = { component.closeDialog() },
                onSend = { mapEvent, shortMessage, message ->
                    component.reportAction(
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
                onClose = { component.closeDialog() },
                onSend = { mapEvent, shortMessage, message ->
                    component.reportAction(
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