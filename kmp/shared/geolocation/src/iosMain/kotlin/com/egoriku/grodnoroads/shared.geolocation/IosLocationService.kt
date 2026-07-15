package com.egoriku.grodnoroads.shared.geolocation

import com.egoriku.grodnoroads.logger.logD
import kotlin.coroutines.resume
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import platform.CoreLocation.CLLocation
import platform.Foundation.NSError

class IosLocationService : LocationService {

    private val locationDelegate = LocationDelegate()
    private var lastKnownLocation: LocationInfo? = null
    private val requestMutex = Mutex()

    // Note: must be collected from a single collector — LocationDelegate has a single listener slot
    override fun locationUpdates(): Flow<LocationInfo?> = callbackFlow {
        locationDelegate.startUpdatingLocation()
        locationDelegate.monitorLocation { location ->
            trySend(location.toLocationInfo())
        }
        awaitClose {
            locationDelegate.stopTracking()
        }
    }

    override suspend fun getLastKnownLocation(): LocationInfo? {
        if (lastKnownLocation == null) {
            lastKnownLocation = requestCurrentLocation()
        }

        return lastKnownLocation
    }

    override suspend fun requestCurrentLocation(): LocationInfo? = requestLocation()

    private suspend fun requestLocation(): LocationInfo? = requestMutex.withLock {
        return suspendCancellableCoroutine { continuation ->
            val callback = { error: NSError?, location: CLLocation? ->
                if (continuation.isActive) {
                    if (location != null) {
                        continuation.resume(location.toLocationInfo())
                    } else {
                        logD("requestLocation error=${error?.localizedDescription}")
                        continuation.resume(null)
                    }
                }
            }

            continuation.invokeOnCancellation {
                locationDelegate.cancelRequest()
            }

            locationDelegate.requestLocation(callback)
        }
    }
}
