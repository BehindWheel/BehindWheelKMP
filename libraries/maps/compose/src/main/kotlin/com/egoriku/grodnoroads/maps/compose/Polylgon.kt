package com.egoriku.grodnoroads.maps.compose

import androidx.compose.runtime.*
import com.google.android.gms.maps.model.Polygon
import com.google.android.gms.maps.model.PolygonOptions

context(MapUpdater)
@Composable
fun rememberPolygon(
    tag: String,
    polygonOptions: () -> PolygonOptions,
): Polygon? {
    var polygon by remember { mutableStateOf<Polygon?>(null) }

    DisposableEffect(tag) {
        polygon = addPolygon(polygonOptions = polygonOptions())

        onDispose {
            polygon?.remove()
        }
    }

    return polygon
}