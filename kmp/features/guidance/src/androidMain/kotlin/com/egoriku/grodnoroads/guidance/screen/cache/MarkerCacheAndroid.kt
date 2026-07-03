package com.egoriku.grodnoroads.guidance.screen.cache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import android.util.LruCache
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.toBitmap
import com.egoriku.grodnoroads.compose.resources.R
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
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MarkerCacheAndroid(private val context: Context) : MarkerCache {

    private val maxSize = (Runtime.getRuntime().maxMemory() / 1024 / 8).toInt()
    private val lruCache = LruCache<String, BitmapDescriptor>(maxSize)

    override fun getOrPut(availableMarkers: AvailableMarkers): MarkerImage {
        return when (val cachedBitmap = lruCache.get(availableMarkers.name)) {
            null -> {
                val id = when (availableMarkers) {
                    Stationary -> R.drawable.nt_ic_marker_stationary_camera
                    StationarySmall -> R.drawable.nt_ic_marker_stationary_camera_small
                    NavigationArrow -> R.drawable.nt_ic_navigation_arrow
                    MediumSpeed -> R.drawable.nt_ic_marker_medium_speed_camera
                    MediumSpeedSmall -> R.drawable.nt_ic_marker_medium_speed_camera_small
                    Mobile -> R.drawable.nt_ic_marker_mobile_camera
                    MobileSmall -> R.drawable.nt_ic_marker_mobile_camera_small
                    Police -> R.drawable.nt_ic_marker_police
                    RoadIncident -> R.drawable.nt_ic_marker_road_incident
                    CarCrash -> R.drawable.nt_ic_marker_car_crash
                    TrafficJam -> R.drawable.nt_ic_marker_traffic_jam
                    WildAnimals -> R.drawable.nt_ic_marker_wild_animals
                }

                val imageBitmap = context.drawableCompat(id).toBitmap()
                BitmapDescriptorFactory.fromBitmap(imageBitmap)
                    .also { bitmapDescriptor ->
                        lruCache.put(availableMarkers.name, bitmapDescriptor)
                    }
            }
            else -> cachedBitmap
        }
    }

    override fun getOrPutCircle(availableMarkers: AvailableMarkers): MarkerImage {
        val key = "circle_${availableMarkers.name}"

        return when (val cachedBitmap = lruCache.get(key)) {
            null -> {
                val bitmap = availableMarkers.circleColor.toArgb().toCircleBitmap(context)
                BitmapDescriptorFactory.fromBitmap(bitmap)
                    .also { bitmapDescriptor ->
                        lruCache.put(key, bitmapDescriptor)
                    }
            }
            else -> cachedBitmap
        }
    }
}

private fun Context.drawableCompat(id: Int) = requireNotNull(ContextCompat.getDrawable(this, id))

private const val CIRCLE_DIAMETER_DP = 12
private const val STROKE_WIDTH_DP = 2

private fun Int.toCircleBitmap(context: Context): Bitmap {
    val density = context.resources.displayMetrics.density
    val diameter = (CIRCLE_DIAMETER_DP * density).toInt()
    val strokeWidth = STROKE_WIDTH_DP * density

    val bitmap = createBitmap(diameter, diameter)
    val canvas = Canvas(bitmap)
    val radius = diameter / 2f

    val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = this@toCircleBitmap
        style = Paint.Style.FILL
    }
    val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = AndroidColor.WHITE
        style = Paint.Style.STROKE
        this.strokeWidth = strokeWidth
    }

    canvas.drawCircle(radius, radius, radius - strokeWidth / 2, fillPaint)
    canvas.drawCircle(radius, radius, radius - strokeWidth / 2, strokePaint)

    return bitmap
}
