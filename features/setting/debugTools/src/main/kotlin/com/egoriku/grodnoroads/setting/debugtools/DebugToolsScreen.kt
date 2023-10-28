package com.egoriku.grodnoroads.setting.debugtools

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.maps.compose.GoogleMap
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.impl.onMapScope
import com.egoriku.grodnoroads.maps.core.StableLatLng
import com.egoriku.grodnoroads.setting.debugtools.data.PolylineRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.ktx.model.cameraPosition
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal sealed interface State {
    data object None : State
    data class Loaded(val polyline: PersistentList<Polyline>) : State
}

internal data class Polyline(
    val name: String,
    val points: PersistentList<LatLng>,
    val bounds: PersistentList<StableLatLng> = calculateBounds(points)
) {
    fun copy(points: PersistentList<LatLng>) = Polyline(
        name = name,
        points = points,
        bounds = calculateBounds(points)
    )
}

private fun calculateBounds(points: PersistentList<LatLng>): PersistentList<StableLatLng> {
    val builder = LatLngBounds.builder()
    for (latLng in points) {
        builder.include(latLng)
    }
    val pointsBounds = builder.build()

    return persistentListOf(
        StableLatLng(
            pointsBounds.northeast.latitude,
            pointsBounds.northeast.longitude
        ),
        StableLatLng(
            pointsBounds.southwest.latitude,
            pointsBounds.northeast.longitude
        ),
        StableLatLng(
            pointsBounds.southwest.latitude,
            pointsBounds.southwest.longitude
        ),
        StableLatLng(
            pointsBounds.northeast.latitude,
            pointsBounds.southwest.longitude
        )
    )
}

@Composable
fun DebugToolsScreen(onBack: () -> Unit) {
    val repository = remember { PolylineRepository() }

    Surface {
        val state by repository.polylines.collectAsState()

        var isMapLoaded by rememberMutableState { false }
        var mapUpdater by rememberMutableState<MapUpdater?> { null }

        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                contentPadding = WindowInsets.navigationBars.asPaddingValues(),
                cameraPositionProvider = {
                    cameraPosition {
                        target(LatLng(53.6687765, 23.8212226))
                        zoom(12.5f)
                    }
                },
                onMapLoaded = { isMapLoaded = true },
                onMapUpdaterChanged = { mapUpdater = it }
            )
            TopActions(
                modifier = Modifier.align(Alignment.TopStart),
                onBack = onBack,
                zoomIn = {
                    mapUpdater.onMapScope {
                        zoomIn()
                    }
                },
                zoomOut = {
                    mapUpdater.onMapScope {
                        zoomOut()
                    }
                }
            )
        }

        mapUpdater.onMapScope {
            when (val state = state) {
                is State.Loaded -> {
                    state.polyline.forEach {
                        PolygonMarker(
                            polyline = it,
                            onPointsChanged = repository::onPointsChanged
                        )
                    }
                }
                State.None -> {}
            }
        }
    }
}

@Composable
private fun TopActions(
    modifier: Modifier,
    onBack: () -> Unit,
    zoomIn: () -> Unit,
    zoomOut: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Row {
            FilledIconButton(
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
        FilledIconButton(
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            onClick = zoomIn
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        }
        FilledIconButton(
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            onClick = zoomOut
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = null
            )
        }
    }
}