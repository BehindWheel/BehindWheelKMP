package com.egoriku.grodnoroads.shared.geolocation

import com.egoriku.grodnoroads.coroutines.flow.nullable.CNullableMutableStateFlow
import com.egoriku.grodnoroads.location.LatLng
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLDistanceFilterNone
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class IosLocationService : LocationService {

    private val locationManager = CLLocationManager()
    private val locationDelegate = LocationDelegate().apply {
        onLocationUpdate = { location ->
            if (location != null) {
                lastLocationFlow.tryEmit(location)
            }
        }
    }

    private var lastKnownLocation: LocationInfo? = null

    override val lastLocationFlow = CNullableMutableStateFlow<LocationInfo>(null)

    override fun startLocationUpdates() {
        locationManager.stopUpdatingLocation()
        locationManager.delegate = null

        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.distanceFilter = kCLDistanceFilterNone
        locationManager.startUpdatingLocation()

        locationManager.delegate = locationDelegate
    }

    override fun stopLocationUpdates() {
        locationManager.stopUpdatingLocation()
        locationManager.delegate = null
    }

    @OptIn(ExperimentalForeignApi::class)
    override suspend fun getLastKnownLocation(): LocationInfo? {
        if (lastKnownLocation == null) {
            val location = locationManager.location ?: return null

            location.coordinate.useContents {
                lastKnownLocation = LocationInfo(
                    latLng = LatLng(latitude, longitude),
                    bearing = location.course.toFloat(),
                    speed = location.speed.toInt()
                )
            }
        }

        return lastKnownLocation
    }

    override suspend fun requestCurrentLocation(): LocationInfo = requestLocation()

    private suspend fun requestLocation(): LocationInfo = suspendCoroutine { continuation ->
        locationManager.delegate = null
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.distanceFilter = kCLDistanceFilterNone
        locationManager.startUpdatingLocation()

        locationManager.delegate = LocationDelegate().apply {
            onLocationUpdate = { location ->
                locationManager.stopUpdatingLocation()
                locationManager.delegate = null

                if (location != null) {
                    continuation.resume(location)
                } else {
                    continuation.resumeWithException(Exception("Unable to get current location"))
                }
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private class LocationDelegate : NSObject(), CLLocationManagerDelegateProtocol {
        var onLocationUpdate: ((LocationInfo?) -> Unit)? = null

        override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
            didUpdateLocations.firstOrNull()?.let {
                val location = it as CLLocation
                location.coordinate.useContents {
                    onLocationUpdate?.invoke(
                        LocationInfo(
                            latLng = LatLng(latitude, longitude),
                            bearing = location.course.toFloat(),
                            speed = location.speed.toInt()
                        )
                    )
                }
            }
        }

        override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
            onLocationUpdate?.invoke(null)
        }
    }
}