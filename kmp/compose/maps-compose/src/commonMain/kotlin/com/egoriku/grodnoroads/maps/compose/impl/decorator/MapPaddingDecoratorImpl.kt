package com.egoriku.grodnoroads.maps.compose.impl.decorator

import com.egoriku.grodnoroads.maps.compose.extension.GoogleMap
import com.egoriku.grodnoroads.maps.compose.extension.updatePadding

internal class MapPaddingDecoratorImpl(
    private val googleMap: GoogleMap
) : MapPaddingDecorator {

    private var mapPadding = MapPadding()
    private var additionalPadding = MapPadding()

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
        setPadding(mapPadding + additionalPadding)
    }

    override fun additionalPadding(left: Int, top: Int, right: Int, bottom: Int) {
        if (left == additionalPadding.paddingLeft &&
            top == additionalPadding.paddingTop &&
            right == additionalPadding.paddingRight &&
            bottom == additionalPadding.paddingBottom
        ) return

        additionalPadding = additionalPadding.copy(
            paddingLeft = left,
            paddingTop = top,
            paddingRight = right,
            paddingBottom = bottom,
        )
        setPadding(mapPadding + additionalPadding)
    }

    private fun setPadding(mapPadding: MapPadding) {
        googleMap.updatePadding(
            left = mapPadding.paddingLeft,
            top = mapPadding.paddingTop,
            right = mapPadding.paddingRight,
            bottom = mapPadding.paddingBottom
        )
    }

    internal data class MapPadding(
        var paddingLeft: Int = 0,
        var paddingTop: Int = 0,
        var paddingRight: Int = 0,
        var paddingBottom: Int = 0,
    )

    internal operator fun MapPadding.plus(mapPadding: MapPadding): MapPadding {
        return MapPadding(
            paddingLeft = paddingLeft + mapPadding.paddingLeft,
            paddingTop = paddingTop + mapPadding.paddingTop,
            paddingRight = paddingRight + mapPadding.paddingRight,
            paddingBottom = paddingBottom + mapPadding.paddingBottom
        )
    }
}