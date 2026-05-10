package com.egoriku.grodnoroads.maps.compose

import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.GMSServices

@Suppress("unused")
object GoogleMapsInit {

    fun start() {
        GMSServices.provideAPIKey(MapsConfig.apiKey)
    }
}
