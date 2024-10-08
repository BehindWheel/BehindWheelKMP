package com.egoriku.grodnoroads.guidance.screen.util

import androidx.compose.runtime.Composable
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage
import com.egoriku.grodnoroads.location.requester.LocationRequestStatus

interface SnackbarMessageBuilder {
    fun handleDriveModeRequest(locationRequestStatus: LocationRequestStatus): SnackbarMessage?
    fun handleCurrentLocationRequest(locationRequestStatus: LocationRequestStatus): SnackbarMessage?
}

@Composable
expect fun rememberSnackbarMessageBuilder(): SnackbarMessageBuilder