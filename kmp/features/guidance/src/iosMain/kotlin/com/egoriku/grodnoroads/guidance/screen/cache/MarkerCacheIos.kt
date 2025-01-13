package com.egoriku.grodnoroads.guidance.screen.cache

import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import platform.Foundation.NSBundle
import platform.Foundation.NSCache
import platform.UIKit.UIImage

class MarkerCacheIos : MarkerCache {

    private val cache: NSCache = NSCache()

    override fun getOrPut(availableMarkers: AvailableMarkers): MarkerImage {
        return when (val cachedImage = cache.objectForKey(availableMarkers.name)) {
            null -> {
                val uiImage = when (availableMarkers) {
                    AvailableMarkers.Stationary -> "nt_ic_marker_stationary_camera"
                    AvailableMarkers.StationarySmall -> "nt_ic_marker_stationary_camera_small"
                    AvailableMarkers.NavigationArrow -> "nt_ic_navigation_arrow"
                    AvailableMarkers.MediumSpeed -> "nt_ic_marker_medium_speed_camera"
                    AvailableMarkers.MediumSpeedSmall -> "nt_ic_marker_medium_speed_camera_small"
                    AvailableMarkers.Mobile -> "nt_ic_marker_mobile_camera"
                    AvailableMarkers.MobileSmall -> "nt_ic_marker_mobile_camera_small"
                    AvailableMarkers.Police -> "nt_ic_marker_police"
                    AvailableMarkers.RoadIncident -> "nt_ic_marker_road_incident"
                    AvailableMarkers.CarCrash -> "nt_ic_marker_car_crash"
                    AvailableMarkers.TrafficJam -> "nt_ic_marker_traffic_jam"
                    AvailableMarkers.WildAnimals -> "nt_ic_marker_wild_animals"
                }.toUIImage() ?: error("icon not found: ${availableMarkers.name}")

                cache.setObject(obj = uiImage, forKey = availableMarkers.name)
                uiImage
            }
            else -> cachedImage as UIImage
        }
    }
}

private fun String.toUIImage(): UIImage? {
    return UIImage.imageNamed(
        name = this,
        inBundle = NSBundle.mainBundle,
        compatibleWithTraitCollection = null
    )
}
