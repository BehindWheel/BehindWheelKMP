package com.egoriku.grodnoroads.maps.compose

import cocoapods.GoogleMaps.GMSServices

@Suppress("unused")
object GoogleMapsInit {

    fun start() {
        GMSServices.provideAPIKey(MapsConfig.apiKey)
    }
}
