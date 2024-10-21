package com.egoriku.grodnoroads.shared.geolocation

import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLDistanceFilterNone
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject

internal class LocationDelegate :
    NSObject(),
    CLLocationManagerDelegateProtocol {

    private val manager = CLLocationManager()
    private var isTracking: Boolean = false

    private var oneTimeLocationCallback: ((NSError?, CLLocation?) -> Unit)? = null
    private var locationCallback: ((CLLocation) -> Unit)? = null

    init {
        manager.delegate = this
    }

    fun monitorLocation(callback: (CLLocation) -> Unit) {
        locationCallback = callback
    }

    fun requestLocation(callback: (NSError?, CLLocation?) -> Unit) {
        oneTimeLocationCallback = callback
        manager.requestLocation()
    }

    fun startUpdatingLocation() {
        if (isTracking) return

        manager.desiredAccuracy = kCLLocationAccuracyBest
        manager.distanceFilter = kCLDistanceFilterNone
        manager.startUpdatingLocation()
        isTracking = true
    }

    fun stopTracking() {
        manager.stopUpdatingLocation()
        isTracking = false
    }

    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        @Suppress("UNCHECKED_CAST")
        val locations = didUpdateLocations as? List<CLLocation>

        locationCallback?.let { callback -> locations?.forEach(callback) }

        oneTimeLocationCallback?.let { oneTimeCallback ->
            val location = locations?.firstOrNull()
            if (location != null) {
                oneTimeCallback(null, location)
                oneTimeLocationCallback = null
            }
        }
    }

    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        oneTimeLocationCallback?.let { oneTimeCallback ->
            oneTimeCallback(didFailWithError, null)
            oneTimeLocationCallback = null
        }
    }
}
