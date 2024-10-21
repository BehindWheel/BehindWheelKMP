package com.egoriku.grodnoroads.shared.geolocation

import com.egoriku.grodnoroads.logger.logD
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.flow.MutableStateFlow

class IosLocationService : LocationService {

    private val locationDelegate = LocationDelegate()
    private var lastKnownLocation: LocationInfo? = null

    init {
        locationDelegate.monitorLocation { location ->
            lastLocationFlow.tryEmit(location.toLocationInfo())
        }
    }

    override val lastLocationFlow = MutableStateFlow<LocationInfo?>(null)

    override fun startLocationUpdates() = locationDelegate.startUpdatingLocation()

    override fun stopLocationUpdates() = locationDelegate.stopTracking()

    override suspend fun getLastKnownLocation(): LocationInfo? {
        if (lastKnownLocation == null) {
            lastKnownLocation = requestLocation()
        }

        return lastKnownLocation
    }

    override suspend fun requestCurrentLocation(): LocationInfo? = requestLocation()

    private suspend fun requestLocation(): LocationInfo? {
        return suspendCoroutine { continuation ->
            locationDelegate.requestLocation { error, location ->
                if (location != null) {
                    continuation.resume(location.toLocationInfo())
                } else {
                    logD("requestLocation error=${error?.localizedDescription}")
                    continuation.resume(null)
                }
            }
        }
    }
}
