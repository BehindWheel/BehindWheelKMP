package com.egoriku.grodnoroads.maps.compose.updater

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.calc.computeOffset
import com.egoriku.grodnoroads.location.calc.distanceTo
import com.egoriku.grodnoroads.location.calc.headingTo
import com.egoriku.grodnoroads.location.calc.roundDistanceTo
import com.egoriku.grodnoroads.maps.compose.core.Marker
import com.egoriku.grodnoroads.maps.compose.core.Projection
import com.egoriku.grodnoroads.maps.compose.impl.MapStateUpdater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

internal const val NAVIGATION_CAMERA_TILT = 55.0
private const val MINIMUM_DISTANCE_FOR_ANIMATION = 5

abstract class MapUpdaterBase :
    MapUpdater,
    MapStateUpdater {

    protected val scope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    protected var minZoom = -1f
    protected var maxZoom = -1f
    protected var lastLocation: LatLng? = null
    protected var lastZoom: Float? = null

    protected abstract val currentZoom: Float
    protected abstract val projection: Projection

    private val _clickedMarker = MutableSharedFlow<Marker>(replay = 0)
    override val clickedMarker: SharedFlow<Marker> = _clickedMarker.asSharedFlow()

    private val _mapLongClickEvents = MutableSharedFlow<LatLng>(replay = 0)
    override val mapLongClickEvents: SharedFlow<LatLng> = _mapLongClickEvents.asSharedFlow()

    protected suspend fun emitClickedMarker(marker: Marker) {
        _clickedMarker.emit(marker)
    }

    protected suspend fun emitMapLongClickEvent(latLng: LatLng) {
        _mapLongClickEvents.emit(latLng)
    }

    override fun setMaxZoomPreference(value: Float) {
        maxZoom = value
    }

    override fun setMinZoomPreference(value: Float) {
        minZoom = value
    }

    override fun resetLastLocation() {
        lastLocation = null
    }

    override fun detach() {
        scope.cancel()
    }

    protected fun shouldZoomIn(): Boolean = currentZoom < maxZoom

    protected fun shouldZoomOut(): Boolean = currentZoom > minZoom

    protected fun shouldAnimateWithInitialCamera(zoom: Float): Boolean {
        return lastLocation == null || lastZoom != zoom || currentZoom != lastZoom
    }

    protected fun updateLastLocationAndZoom(location: LatLng, zoom: Float) {
        lastZoom = zoom
        lastLocation = location
    }

    protected abstract fun Projection.getCenterLocation(): LatLng
    protected abstract fun Projection.getOffsetLocation(): LatLng

    protected abstract fun animateShadowCamera(shadowTarget: LatLng, bearing: Double, zoom: Float)

    fun animateWithShadowPoint(target: LatLng, zoom: Float) {
        val projection = projection
        val result = calculateShadowTargetOrNull(
            target = target,
            getCenterLocation = { projection.getCenterLocation() },
            getOffsetLocation = { projection.getOffsetLocation() }
        ) ?: return

        val (shadowTarget, bearing) = result
        animateShadowCamera(shadowTarget, bearing, zoom)
    }

    private fun calculateShadowTargetOrNull(
        target: LatLng,
        getCenterLocation: () -> LatLng,
        getOffsetLocation: () -> LatLng
    ): Pair<LatLng, Double>? {
        val lastLoc = lastLocation ?: return null
        val distance = lastLoc roundDistanceTo target
        if (distance < MINIMUM_DISTANCE_FOR_ANIMATION) return null

        val bearing = lastLoc headingTo target

        val centerLocation = getCenterLocation()
        val offsetLocation = getOffsetLocation()
        val offsetDistance = centerLocation distanceTo offsetLocation

        val shadowTarget = computeOffset(target, offsetDistance, bearing)
        return shadowTarget to bearing
    }
}
