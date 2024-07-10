package com.egoriku.grodnoroads.maps.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.egoriku.grodnoroads.maps.compose.api.CameraMoveState
import com.egoriku.grodnoroads.maps.compose.api.ZoomLevelState
import com.egoriku.grodnoroads.maps.compose.configuration.DefaultMapProperties
import com.egoriku.grodnoroads.maps.compose.configuration.DefaultMapUiSettings
import com.egoriku.grodnoroads.maps.compose.configuration.MapProperties
import com.egoriku.grodnoroads.maps.compose.configuration.MapUiSettings
import com.egoriku.grodnoroads.maps.compose.core.CameraPosition
import com.egoriku.grodnoroads.maps.compose.core.Projection
import com.egoriku.grodnoroads.maps.compose.extension.GoogleMap
import com.egoriku.grodnoroads.maps.compose.updater.MapUpdater

@Composable
expect fun GoogleMap(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Unspecified,
    contentPadding: PaddingValues = PaddingValues(),
    mapProperties: MapProperties = DefaultMapProperties,
    mapUiSettings: MapUiSettings = DefaultMapUiSettings,
    onMapLoaded: (GoogleMap) -> Unit = {},
    cameraPositionProvider: () -> CameraPosition,
    onMapUpdaterChanged: (MapUpdater?) -> Unit = {},
    onProjectionChanged: (Projection) -> Unit = {},
    onZoomChanged: (ZoomLevelState) -> Unit = {},
    cameraMoveStateChanged: (CameraMoveState) -> Unit = {}
)