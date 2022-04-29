package com.egoriku.grodnoroads.screen.map

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.egoriku.grodnoroads.CameraViewModel
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.domain.model.AppMode
import com.egoriku.grodnoroads.domain.model.UserActionType
import com.egoriku.grodnoroads.screen.map.defaultmode.MapMode
import com.egoriku.grodnoroads.screen.map.defaultmode.ui.DrawerButton
import com.egoriku.grodnoroads.screen.map.drivemode.DriveMode
import com.egoriku.grodnoroads.screen.map.ui.GoogleMapView
import org.koin.androidx.compose.viewModel

class MapScreenTab(private val openDrawer: () -> Unit) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(R.string.tab_map)
            val icon = rememberVectorPainter(Icons.Default.Explore)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val cameraViewModel by viewModel<CameraViewModel>()

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {
            val stationary by cameraViewModel.stationary.collectAsState()
            val location by cameraViewModel.location.collectAsState()
            val mode by cameraViewModel.mode.collectAsState()
            val userActions by cameraViewModel.userActions.collectAsState(initial = emptyList())

            Box(modifier = Modifier.fillMaxSize()) {
                GoogleMapView(
                    modifier = Modifier
                        .fillMaxSize(),
                    stationary = stationary,
                    userPosition = location,
                    userActions = userActions
                )

                when (mode) {
                    AppMode.Map -> MapMode(
                        startNavigation = {
                            cameraViewModel.startLocationUpdates()
                        }
                    )
                    AppMode.Drive -> DriveMode(
                        stopDrive = {
                            cameraViewModel.stopLocationUpdates()
                        },
                        reportPolice = {
                            cameraViewModel.reportAction(
                                latLng = location.latLng,
                                type = UserActionType.Police
                            )
                        },
                        reportAccident = {
                            cameraViewModel.reportAction(
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
}