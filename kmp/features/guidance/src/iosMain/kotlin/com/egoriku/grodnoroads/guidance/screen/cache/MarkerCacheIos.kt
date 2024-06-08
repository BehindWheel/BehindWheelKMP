package com.egoriku.grodnoroads.guidance.screen.cache

import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import com.egoriku.grodnoroads.shared.resources.MR
import platform.Foundation.NSCache
import platform.UIKit.UIImage

class MarkerCacheIos : MarkerCache {

    private val cache: NSCache = NSCache()

    override fun getOrPut(availableMarkers: AvailableMarkers): MarkerImage {
        return when (val cachedImage = cache.objectForKey(availableMarkers.name)) {
            null -> {
                val uiImage = when (availableMarkers) {
                    AvailableMarkers.Stationary -> MR.images.nt_ic_marker_stationary_camera
                    AvailableMarkers.StationarySmall -> MR.images.nt_ic_marker_stationary_camera_small
                    AvailableMarkers.NavigationArrow -> MR.images.nt_ic_navigation_arrow
                    AvailableMarkers.MediumSpeed -> MR.images.nt_ic_marker_medium_speed_camera
                    AvailableMarkers.MediumSpeedSmall -> MR.images.nt_ic_marker_medium_speed_camera_small
                    AvailableMarkers.Mobile -> MR.images.nt_ic_marker_mobile_camera
                    AvailableMarkers.MobileSmall -> MR.images.nt_ic_marker_mobile_camera_small
                    AvailableMarkers.Police -> MR.images.nt_ic_marker_police
                    AvailableMarkers.RoadIncident -> MR.images.nt_ic_marker_road_incident
                    AvailableMarkers.CarCrash -> MR.images.nt_ic_marker_car_crash
                    AvailableMarkers.TrafficJam -> MR.images.nt_ic_marker_traffic_jam
                    AvailableMarkers.WildAnimals -> MR.images.nt_ic_marker_wild_animals
                }.toUIImage() ?: error("icon not found: ${availableMarkers.name}")

                cache.setObject(obj = uiImage, forKey = availableMarkers.name)
                uiImage
            }
            else -> cachedImage as UIImage
        }
    }
}