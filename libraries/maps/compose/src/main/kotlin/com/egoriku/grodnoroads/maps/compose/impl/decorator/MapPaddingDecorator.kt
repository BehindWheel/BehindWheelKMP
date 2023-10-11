package com.egoriku.grodnoroads.maps.compose.impl.decorator

import com.egoriku.grodnoroads.maps.compose.decorator.MapPaddingDecorator
import com.google.android.gms.maps.GoogleMap

internal class MapPaddingDecoratorImpl(
    private val googleMap: GoogleMap
) : MapPaddingDecorator {

    private var mapPadding = MapPadding()

    override fun updateContentPadding(left: Int, top: Int, right: Int, bottom: Int) {
        if (left == mapPadding.paddingLeft &&
            top == mapPadding.paddingTop &&
            right == mapPadding.paddingRight &&
            bottom == mapPadding.paddingBottom
        ) return

        mapPadding = mapPadding.copy(
            paddingLeft = left,
            paddingTop = top,
            paddingRight = right,
            paddingBottom = bottom,
        )
        setPadding(mapPadding)
    }

    private fun setPadding(mapPadding: MapPadding) {
        googleMap.setPadding(
            /* left = */ mapPadding.paddingLeft,
            /* top = */ mapPadding.paddingTop,
            /* right = */ mapPadding.paddingRight,
            /* bottom = */ mapPadding.paddingBottom
        )
    }

    internal data class MapPadding(
        var paddingLeft: Int = 0,
        var paddingTop: Int = 0,
        var paddingRight: Int = 0,
        var paddingBottom: Int = 0,
    )
}