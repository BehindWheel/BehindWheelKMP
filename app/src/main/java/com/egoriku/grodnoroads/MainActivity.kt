package com.egoriku.grodnoroads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.egoriku.grodnoroads.domain.model.AppMode
import com.egoriku.grodnoroads.domain.model.UserActionType
import com.egoriku.grodnoroads.ui.GoogleMapView
import com.egoriku.grodnoroads.ui.mode.drive.DriveMode
import com.egoriku.grodnoroads.ui.mode.map.MapMode
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val cameraViewModel: CameraViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setStatusBarColor(Color.Transparent, darkIcons = useDarkIcons)
            }

            GrodnoRoadsTheme {
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
    }
}