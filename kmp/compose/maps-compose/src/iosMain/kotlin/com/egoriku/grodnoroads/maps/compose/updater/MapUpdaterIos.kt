package com.egoriku.grodnoroads.maps.compose.updater

import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.animateWithCameraUpdate
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.calc.computeOffset
import com.egoriku.grodnoroads.location.calc.distanceTo
import com.egoriku.grodnoroads.location.calc.headingTo
import com.egoriku.grodnoroads.location.calc.roundDistanceTo
import com.egoriku.grodnoroads.location.toLatLng
import com.egoriku.grodnoroads.maps.compose.core.Marker
import com.egoriku.grodnoroads.maps.compose.extension.GoogleMap
import com.egoriku.grodnoroads.maps.compose.extension.zoom
import com.egoriku.grodnoroads.maps.compose.impl.MapStateUpdater
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecorator
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecoratorImpl
import com.egoriku.grodnoroads.maps.compose.marker.MarkerOptions
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import platform.CoreGraphics.CGPoint
import platform.CoreGraphics.CGPointMake
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.QuartzCore.CATransaction

@OptIn(ExperimentalForeignApi::class)
class MapUpdaterIos(
    private val googleMap: GoogleMap,
    override val paddingDecorator: MapPaddingDecorator = MapPaddingDecoratorImpl(googleMap),
    private val onZoomChanged: () -> Unit
) : MapUpdater,
    MapStateUpdater,
    MapPaddingDecorator by paddingDecorator {

    private val scope = CoroutineScope(Dispatchers.Main)

    private var minZoom = -1f
    private var maxZoom = -1f

    private val bottomRightPoint: CGPoint by lazy {
        val projection = googleMap.projection
        val point = projection.visibleRegion().useContents { nearRight }

        projection
            .pointForCoordinate(CLLocationCoordinate2DMake(point.latitude, point.longitude))
            .useContents { this }
    }
    private val center by lazy {
        CGPointMake(
            x = bottomRightPoint.x / 2,
            y = bottomRightPoint.y / 2
        )
    }
    private val offset by lazy {
        CGPointMake(
            x = center.useContents { x },
            y = 2 * bottomRightPoint.y / 3
        )
    }

    private var lastLocation: LatLng? = null
    private var lastZoom: Float? = null

    private val currentZoom: Float
        get() = googleMap.zoom

    private val _clickedMarker = MutableSharedFlow<Marker?>(replay = 0)
    override val clickedMarker: SharedFlow<Marker?> = _clickedMarker.asSharedFlow()

    fun clickMarker(marker: Marker) {
        scope.launch {
            _clickedMarker.emit(marker)
        }
    }

    override fun attach() = Unit

    override fun detach() {
        scope.cancel()
    }

    override fun setMaxZoomPreference(value: Float) {
        maxZoom = value
    }

    override fun setMinZoomPreference(value: Float) {
        minZoom = value
    }

    override fun isInitialCameraAnimation() = lastLocation == null
    override fun resetLastLocation() {
        lastLocation = null
    }

    override fun addMarker(markerOptions: MarkerOptions): Marker? {
        val marker = Marker.markerWithPosition(
            position = markerOptions.position.cValue
        ).apply {
            icon = markerOptions.icon

            if (markerOptions.rotation != null) {
                rotation = markerOptions.rotation.toDouble()
            }
            map = googleMap
            tappable = true
        }
        return marker
    }

    override fun zoomIn() {
        onZoomChanged()
        if (currentZoom >= maxZoom) return
        googleMap.animateWithCameraUpdate(GMSCameraUpdate.zoomIn())
    }

    override fun zoomOut() {
        onZoomChanged()
        if (currentZoom <= minZoom) return
        googleMap.animateWithCameraUpdate(GMSCameraUpdate.zoomOut())
    }

    override fun animateCurrentLocation(target: LatLng, zoom: Float, bearing: Float) {
        additionalPadding(top = (googleMap.frame.useContents { size.height } / 3).toInt())

        animateCamera(
            cameraUpdate = GMSCameraUpdate.setCamera(
                GMSCameraPosition(
                    target = target.cValue,
                    bearing = bearing.toDouble(),
                    zoom = zoom,
                    viewingAngle = 35.0
                )
            ),
            duration = 0.7,
            onFinish = { additionalPadding(top = 0) }
        )
    }

    override fun animateCamera(target: LatLng, zoom: Float, bearing: Float) {
        if (lastLocation == null || lastZoom != zoom || googleMap.zoom != lastZoom) {
            additionalPadding(
                top = (googleMap.frame.useContents { size.height } / 3).toInt()
            )
            animateCamera(
                cameraUpdate = GMSCameraUpdate.setCamera(
                    GMSCameraPosition(
                        target = target.cValue,
                        bearing = bearing.toDouble(),
                        zoom = zoom,
                        viewingAngle = 35.0
                    )
                ),
                duration = 0.7
            )
        } else {
            animateWithShadowPoint(target = target, zoom = zoom)
        }

        lastZoom = zoom
        lastLocation = target
    }

    private fun animateWithShadowPoint(target: LatLng, zoom: Float) {
        val lastLocation = lastLocation ?: return
        val distance = lastLocation roundDistanceTo target
        if (distance < 5) return

        val bearing = lastLocation headingTo target

        val projection = googleMap.projection
        val centerLocation = projection.coordinateForPoint(center).toLatLng()
        val offsetLocation = projection.coordinateForPoint(offset).toLatLng()

        val offsetDistance = centerLocation distanceTo offsetLocation

        val shadowTarget = computeOffset(target, offsetDistance, bearing)
        animateCamera(
            GMSCameraUpdate.setCamera(
                GMSCameraPosition(
                    target = shadowTarget.cValue,
                    bearing = bearing,
                    zoom = zoom,
                    viewingAngle = 35.0
                )
            ),
            duration = 0.7
        )
    }

    override fun animateTarget(
        target: LatLng,
        zoom: Float?,
        onFinish: () -> Unit,
        onCancel: () -> Unit
    ) {
        val bearing = googleMap.camera.bearing

        val zoomLevel = zoom ?: googleMap.zoom
        animateCamera(
            cameraUpdate = GMSCameraUpdate.setCamera(
                GMSCameraPosition(
                    target = target.cValue,
                    bearing = bearing,
                    zoom = zoomLevel,
                    viewingAngle = 0.0
                )
            ),
            duration = 1.0,
            onFinish = onFinish
        )
    }

    override fun animateZoom(zoom: Float) {
        val target = googleMap.camera.target
        val bearing = googleMap.camera.bearing

        animateCamera(
            cameraUpdate = GMSCameraUpdate.setCamera(
                GMSCameraPosition(
                    target = target,
                    bearing = bearing,
                    zoom = zoom,
                    viewingAngle = 0.0
                )
            ),
            duration = 1.0
        )
    }

    private fun animateCamera(
        cameraUpdate: GMSCameraUpdate,
        duration: Double,
        onFinish: () -> Unit = {}
    ) {
        CATransaction.begin()
        CATransaction.setAnimationDuration(duration)
        CATransaction.setCompletionBlock(onFinish)
        googleMap.animateWithCameraUpdate(cameraUpdate)
        CATransaction.commit()
    }
}
