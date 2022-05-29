package com.egoriku.grodnoroads.screen.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.domain.model.AppMode
import com.egoriku.grodnoroads.domain.model.LocationState
import com.egoriku.grodnoroads.domain.model.MapEventType.RoadAccident
import com.egoriku.grodnoroads.domain.model.MapEventType.TrafficPolice
import com.egoriku.grodnoroads.foundation.DrawerButton
import com.egoriku.grodnoroads.screen.map.MapComponent.MapEvent.UserActions
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label.ShowToast
import com.egoriku.grodnoroads.screen.map.ui.GoogleMapView
import com.egoriku.grodnoroads.screen.map.ui.MarkerAlertDialog
import com.egoriku.grodnoroads.screen.map.ui.defaultmode.MapMode
import com.egoriku.grodnoroads.screen.map.ui.drivemode.DriveMode
import com.egoriku.grodnoroads.util.toast

@Composable
fun MapUi(
    component: MapComponent,
    openDrawer: () -> Unit
) {
    // TODO: Open with MVI flow
    var markerState: UserActions? by remember { mutableStateOf(null) }

    MarkerAlertDialog(
        userActions = markerState,
        onClose = {
            markerState = null
        }
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        val location by component.location.collectAsState(LocationState.None)
        val mode by component.appMode.collectAsState(AppMode.Map)
        val mapEvents by component.mapEvents.collectAsState(initial = emptyList())
        val alertMessages by component.alertMessages.collectAsState(initial = emptyList())

        LabelsSubscription(component)

        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMapView(
                modifier = Modifier.fillMaxSize(),
                mapEvents = mapEvents,
                locationState = location,
                onMarkerClick = {
                    markerState = it
                }
            )

            AnimatedVisibility(
                visible = mode == AppMode.Map,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                MapMode(
                    onLocationEnabled = component::startLocationUpdates,
                    onLocationDisabled = component::onLocationDisabled
                )
                DrawerButton(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.TopStart)
                        .statusBarsPadding(),
                    onClick = openDrawer
                )
            }
            AnimatedVisibility(
                visible = mode == AppMode.Drive,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                DriveMode(
                    alertMessages = alertMessages,
                    location = location,
                    stopDrive = component::stopLocationUpdates,
                    reportPolice = {
                        component.reportAction(
                            latLng = location.latLng,
                            type = TrafficPolice
                        )
                    },
                    reportAccident = {
                        component.reportAction(
                            latLng = location.latLng,
                            type = RoadAccident
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun LabelsSubscription(component: MapComponent) {
    val context = LocalContext.current
    val labels by component.labels.collectAsState(initial = Label.None)

    LaunchedEffect(labels) {
        when (val label = labels) {
            is ShowToast -> context.toast(label.message)
            is Label.None -> {}
        }
    }
}