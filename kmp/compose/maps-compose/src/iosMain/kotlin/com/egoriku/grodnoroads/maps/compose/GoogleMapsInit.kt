package com.egoriku.grodnoroads.maps.compose

import cocoapods.GoogleMaps.GMSServices

object GoogleMapsInit {

    fun start() {
        GMSServices.provideAPIKey(MapsConfig.apiKey)
    }
}
