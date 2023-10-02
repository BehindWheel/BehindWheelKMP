package com.egoriku.grodnoroads.maps.compose.impl

internal interface MapStateUpdater {
    fun attach()
    fun detach()

    fun setMaxZoomPreference(value: Float)
    fun setMinZoomPreference(value: Float)
}