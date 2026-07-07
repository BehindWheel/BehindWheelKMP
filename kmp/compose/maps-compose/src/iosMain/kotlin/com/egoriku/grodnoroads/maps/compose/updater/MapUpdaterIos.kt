package com.egoriku.grodnoroads.maps.compose.updater

import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.location.toLatLng
import com.egoriku.grodnoroads.maps.compose.core.Marker
import com.egoriku.grodnoroads.maps.compose.core.Projection
import com.egoriku.grodnoroads.maps.compose.extension.GoogleMap
import com.egoriku.grodnoroads.maps.compose.extension.zoom
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecorator
import com.egoriku.grodnoroads.maps.compose.impl.decorator.MapPaddingDecoratorImpl
import com.egoriku.grodnoroads.maps.compose.marker.MarkerOptions
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.launch
import platform.CoreGraphics.CGPoint
import platform.CoreGraphics.CGPointMake
import platform.CoreLocation.CLLocationCoordinate2DMake
import platform.QuartzCore.CATransaction
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.GMSCameraPosition
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.GMSCameraUpdate
import swiftPMImport.Grodno.Roads.kmp.compose.kmp.compose.maps.compose.animateWithCameraUpdate

@OptIn(ExperimentalForeignApi::class)
class MapUpdaterIos(
    private val googleMap: GoogleMap,
    override val paddingDecorator: MapPaddingDecorator = MapPaddingDecoratorImpl(googleMap),
    private val onZoomChanged: () -> Unit
) : MapUpdaterBase(),
    MapPaddingDecorator by paddingDecorator {

    private val bottomRightPoint: CGPoint by lazy {
        val projection = projection
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

    override val projection: Projection
        get() = googleMap.projection

    override val currentZoom: Float
        get() = googleMap.zoom

    fun clickMarker(marker: Marker) {
        scope.launch {
            emitClickedMarker(marker)
        }
    }

    fun mapLongPressEvent(latLng: LatLng) {
        scope.launch {
            emitMapLongClickEvent(latLng)
        }
    }

    override fun attach() = Unit

    override fun addMarker(markerOptions: MarkerOptions): Marker? {
        val marker = Marker.markerWithPosition(
            position = markerOptions.position.cValue
        ).apply {
            title = markerOptions.title
            icon = markerOptions.icon
            zIndex = markerOptions.zIndex.toInt()

            if (markerOptions.rotation != null) {
                rotation = markerOptions.rotation.toDouble()
            }
            if (markerOptions.anchor != null) {
                groundAnchor = CGPointMake(
                    x = markerOptions.anchor.u.toDouble(),
                    y = markerOptions.anchor.v.toDouble()
                )
            }
            map = googleMap
            tappable = true
        }
        return marker
    }

    override fun zoomIn() {
        onZoomChanged()
        if (!shouldZoomIn()) return
        googleMap.animateWithCameraUpdate(GMSCameraUpdate.zoomIn())
    }

    override fun zoomOut() {
        onZoomChanged()
        if (!shouldZoomOut()) return
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
                    viewingAngle = NAVIGATION_CAMERA_TILT
                )
            ),
            duration = 0.7,
            onFinish = { additionalPadding(top = 0) }
        )
    }

    override fun animateCamera(target: LatLng, zoom: Float, bearing: Float) {
        if (shouldAnimateWithInitialCamera(zoom)) {
            additionalPadding(
                top = (googleMap.frame.useContents { size.height } / 3).toInt()
            )
            animateCamera(
                cameraUpdate = GMSCameraUpdate.setCamera(
                    GMSCameraPosition(
                        target = target.cValue,
                        bearing = bearing.toDouble(),
                        zoom = zoom,
                        viewingAngle = NAVIGATION_CAMERA_TILT
                    )
                ),
                duration = 0.7
            )
        } else {
            animateWithShadowPoint(target = target, zoom = zoom)
        }

        updateLastLocationAndZoom(target, zoom)
    }

    override fun Projection.getCenterLocation(): LatLng = coordinateForPoint(center).toLatLng()

    override fun Projection.getOffsetLocation(): LatLng = coordinateForPoint(offset).toLatLng()

    override fun animateShadowCamera(shadowTarget: LatLng, bearing: Double, zoom: Float) {
        animateCamera(
            GMSCameraUpdate.setCamera(
                GMSCameraPosition(
                    target = shadowTarget.cValue,
                    bearing = bearing,
                    zoom = zoom,
                    viewingAngle = NAVIGATION_CAMERA_TILT
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
