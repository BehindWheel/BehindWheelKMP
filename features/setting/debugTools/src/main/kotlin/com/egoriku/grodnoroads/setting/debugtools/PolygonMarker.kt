package com.egoriku.grodnoroads.setting.debugtools

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.maps.compose.MapUpdater
import com.egoriku.grodnoroads.maps.compose.rememberDraggableMarker
import com.egoriku.grodnoroads.maps.compose.rememberPolygon
import com.egoriku.grodnoroads.maps.core.asStable
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.model.markerOptions
import com.google.maps.android.ktx.model.polygonOptions
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate

context(MapUpdater)
@Composable
internal fun PolygonMarker(
    polyline: Polyline,
    onPointsChanged: (String, PersistentList<LatLng>) -> Unit
) {
    var points by rememberMutableState { polyline.points }
    val boundsPolygon = rememberPolygon(
        tag = "bounds_${polyline.name}",
        polygonOptions = {
            polygonOptions {
                addAll(polyline.bounds.map { it.value })
                strokeColor(Color.Red.toArgb())
            }
        }
    )

    val polygon = rememberPolygon(
        tag = polyline.name,
        polygonOptions = {
            polygonOptions {
                clickable(true)
                addAll(polyline.points)
            }
        }
    )

    LaunchedEffect(points) {
        polygon?.points = points
    }

    LaunchedEffect(polyline) {
        boundsPolygon?.points = polyline.bounds.map { it.value }
    }

    polyline.points.forEachIndexed { index, it ->
        var position by rememberMutableState(it) { it.asStable() }
        val marker = rememberDraggableMarker(
            tag = "${polyline.name}_bounds_$index",
            markerOptions = {
                markerOptions {
                    position(it)
                    draggable(true)
                }
            },
            onPositionChange = { newPosition ->
                val newPoints = polyline.points.mutate {
                    it[index] = newPosition.value
                }
                onPointsChanged(polyline.name, newPoints)
                points = newPoints
                position = newPosition
            }
        )

        LaunchedEffect(position) {
            marker?.position = position.value
        }
    }
}