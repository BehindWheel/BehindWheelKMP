package com.egoriku.grodnoroads.guidance.screen.cache

import com.egoriku.grodnoroads.guidance.screen.cache.MarkerCache.AvailableMarkers
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import com.egoriku.grodnoroads.maps.compose.util.toUIColor
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSBundle
import platform.Foundation.NSCache
import platform.UIKit.UIBezierPath
import platform.UIKit.UIColor
import platform.UIKit.UIGraphicsImageRenderer
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

    override fun getOrPutCircle(availableMarkers: AvailableMarkers): MarkerImage {
        val key = "circle_${availableMarkers.name}"

        return when (val cachedImage = cache.objectForKey(key)) {
            null -> {
                val uiImage = availableMarkers.circleColor.toUIColor().toCircleImage()

                cache.setObject(obj = uiImage, forKey = key)
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

private const val CIRCLE_DIAMETER = 12.0
private const val STROKE_WIDTH = 2.0

@OptIn(ExperimentalForeignApi::class)
private fun UIColor.toCircleImage(): UIImage {
    val rect = CGRectMake(x = 0.0, y = 0.0, width = CIRCLE_DIAMETER, height = CIRCLE_DIAMETER)
    val renderer = UIGraphicsImageRenderer(bounds = rect)
    val fillColor = this

    return renderer.imageWithActions {
        val path = UIBezierPath.bezierPathWithOvalInRect(
            CGRectMake(
                x = STROKE_WIDTH / 2,
                y = STROKE_WIDTH / 2,
                width = CIRCLE_DIAMETER - STROKE_WIDTH,
                height = CIRCLE_DIAMETER - STROKE_WIDTH
            )
        )
        fillColor.setFill()
        path.fill()

        UIColor.whiteColor.setStroke()
        path.lineWidth = STROKE_WIDTH
        path.stroke()
    }
}
