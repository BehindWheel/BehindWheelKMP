package com.egoriku.grodnoroads

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.egoriku.grodnoroads.extension.logD
import com.egoriku.grodnoroads.ui.GoogleMapView
import com.egoriku.grodnoroads.ui.StartDriveModButton
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                ) {
                    val stationary by cameraViewModel.stationary.collectAsState()
                    val location by cameraViewModel.location.collectAsState()

                    GoogleMapView(
                        modifier = Modifier.matchParentSize(),
                        stationary = stationary,
                        userPosition = location
                    )
                    StartDriveModButton(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    ) {
                        logD("Start Navigation")
                        cameraViewModel.startLocationUpdates()
                    }
                }
            }
        }
    }
}