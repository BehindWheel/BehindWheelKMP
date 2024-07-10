package com.egoriku.grodnoroads.guidance.screen.cache

import android.content.Context
import android.util.LruCache
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
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
import com.egoriku.grodnoroads.maps.compose.extension.MarkerImage
import com.egoriku.grodnoroads.shared.resources.MR
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MarkerCacheAndroid(private val context: Context) : MarkerCache {

    private val maxSize = (Runtime.getRuntime().maxMemory() / 1024 / 8).toInt()
    private val lruCache = LruCache<String, BitmapDescriptor>(maxSize)

    override fun getOrPut(availableMarkers: AvailableMarkers): MarkerImage {
        return when (val cachedBitmap = lruCache.get(availableMarkers.name)) {
            null -> {
                val id = when (availableMarkers) {
                    Stationary -> MR.images.nt_ic_marker_stationary_camera
                    StationarySmall -> MR.images.nt_ic_marker_stationary_camera_small
                    NavigationArrow -> MR.images.nt_ic_navigation_arrow
                    MediumSpeed -> MR.images.nt_ic_marker_medium_speed_camera
                    MediumSpeedSmall -> MR.images.nt_ic_marker_medium_speed_camera_small
                    Mobile -> MR.images.nt_ic_marker_mobile_camera
                    MobileSmall -> MR.images.nt_ic_marker_mobile_camera_small
                    Police -> MR.images.nt_ic_marker_police
                    RoadIncident -> MR.images.nt_ic_marker_road_incident
                    CarCrash -> MR.images.nt_ic_marker_car_crash
                    TrafficJam -> MR.images.nt_ic_marker_traffic_jam
                    WildAnimals -> MR.images.nt_ic_marker_wild_animals
                }.drawableResId

                val imageBitmap = context.drawableCompat(id).toBitmap()
                BitmapDescriptorFactory.fromBitmap(imageBitmap)
                    .also { bitmapDescriptor ->
                        lruCache.put(availableMarkers.name, bitmapDescriptor)
                    }
            }
            else -> cachedBitmap
        }
    }
}

private fun Context.drawableCompat(id: Int) = requireNotNull(ContextCompat.getDrawable(this, id))
