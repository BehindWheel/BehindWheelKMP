package com.egoriku.grodnoroads.screen.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.egoriku.grodnoroads.CameraViewModel
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.domain.model.AppMode
import com.egoriku.grodnoroads.domain.model.UserActionType
import com.egoriku.grodnoroads.ui.GoogleMapView
import com.egoriku.grodnoroads.ui.mode.drive.DriveMode
import com.egoriku.grodnoroads.ui.mode.map.MapMode
import org.koin.androidx.compose.viewModel

object MapScreenTab : Tab {

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

            GoogleMapView(
                modifier = Modifier.fillMaxSize(),
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
        }
    }
}