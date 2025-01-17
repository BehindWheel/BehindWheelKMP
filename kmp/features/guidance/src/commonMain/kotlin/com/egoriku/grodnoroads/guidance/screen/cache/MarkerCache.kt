package com.egoriku.grodnoroads.guidance.screen.cache

import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage

interface MarkerCache {

    fun getOrPut(availableMarkers: AvailableMarkers): MarkerImage

    enum class AvailableMarkers {
        Mobile,
        MobileSmall,
        Stationary,
        StationarySmall,
        MediumSpeed,
        MediumSpeedSmall,
        NavigationArrow,
        Police,
        RoadIncident,
        CarCrash,
        TrafficJam,
        WildAnimals
    }
}
