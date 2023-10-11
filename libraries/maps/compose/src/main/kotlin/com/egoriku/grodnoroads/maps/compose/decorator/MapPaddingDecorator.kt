package com.egoriku.grodnoroads.maps.compose.decorator

interface MapPaddingDecorator {

    fun updateContentPadding(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0)
}