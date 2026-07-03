package com.egoriku.grodnoroads.guidance.screen.cache

import androidx.compose.ui.graphics.Color
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.CarCrash
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.MediumSpeed
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.MediumSpeedSmall
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.Mobile
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.MobileSmall
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.NavigationArrow
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.Police
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.RoadIncident
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.Stationary
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.StationarySmall
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.TrafficJam
import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers.WildAnimals

val AvailableMarkers.circleColor: Color
    get() = when (this) {
        Stationary, StationarySmall -> Color(0xFF232F34)
        Mobile, MobileSmall -> Color(0xFFFFD666)
        MediumSpeed, MediumSpeedSmall -> Color(0xFFFFB366)
        Police -> Color(0xFF2053B9)
        RoadIncident, CarCrash, TrafficJam, WildAnimals -> Color(0xFFB3261E)
        NavigationArrow -> error("No circle color defined for $this")
    }
