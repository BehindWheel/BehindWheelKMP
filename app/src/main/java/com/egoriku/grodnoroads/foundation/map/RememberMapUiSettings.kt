package com.egoriku.grodnoroads.foundation.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.maps.android.compose.MapUiSettings

@Composable
fun rememberUiSettings(): MapUiSettings {
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                mapToolbarEnabled = false,
                compassEnabled = false,
                myLocationButtonEnabled = false
            )
        )
    }
    return uiSettings
}
