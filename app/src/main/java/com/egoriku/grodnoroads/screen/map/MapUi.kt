package com.egoriku.grodnoroads.screen.map

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.domain.model.AppMode
import com.egoriku.grodnoroads.domain.model.UserActionType
import com.egoriku.grodnoroads.domain.model.UserPosition
import com.egoriku.grodnoroads.foundation.DrawerButton
import com.egoriku.grodnoroads.screen.map.ui.GoogleMapView
import com.egoriku.grodnoroads.screen.map.ui.defaultmode.MapMode
import com.egoriku.grodnoroads.screen.map.ui.drivemode.DriveMode

@Composable
fun MapUi(openDrawer: () -> Unit, component: MapComponent) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        val stationary by component.stationary.collectAsState(emptyList())
        val location by component.location.collectAsState(UserPosition.None)
        val mode by component.appMode.collectAsState(AppMode.Map)
        val userActions by component.usersActions.collectAsState(initial = emptyList())

        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMapView(
                modifier = Modifier.fillMaxSize(),
                stationary = stationary,
                userPosition = location,
                userActions = userActions
            )

            when (mode) {
                AppMode.Map -> MapMode(
                    startNavigation = component::startLocationUpdates
                )
                AppMode.Drive -> DriveMode(
                    stopDrive = component::stopLocationUpdates,
                    reportPolice = {
                        component.reportAction(
                            latLng = location.latLng,
                            type = UserActionType.Police
                        )
                    },
                    reportAccident = {
                        component.reportAction(
                            latLng = location.latLng,
                            type = UserActionType.Accident
                        )
                    }
                )
            }

            DrawerButton(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.TopStart)
                    .statusBarsPadding(),
                onClick = openDrawer
            )
        }
    }
}