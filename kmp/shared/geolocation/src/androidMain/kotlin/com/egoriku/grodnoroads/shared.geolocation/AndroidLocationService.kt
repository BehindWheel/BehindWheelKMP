package com.egoriku.grodnoroads.shared.geolocation

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.core.location.LocationRequestCompat.QUALITY_HIGH_ACCURACY
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.logger.logD
import com.egoriku.grodnoroads.shared.geolocation.util.toKilometersPerHour
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AndroidLocationService(context: Context) : LocationService {
    private val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

    private var lastKnownLocation: LocationInfo? = null

    // Note: each collector creates a separate LocationCallback — prefer single collector
    @SuppressLint("MissingPermission")
    override fun locationUpdates(): Flow<LocationInfo?> = callbackFlow {
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation ?: return
                trySend(location.toLocationInfo())
            }
        }

        fusedLocationProvider.requestLocationUpdates(
            highPrecisionLowIntervalRequest,
            callback,
            Looper.getMainLooper()
        )

        awaitClose {
            fusedLocationProvider.removeLocationUpdates(callback)
        }
    }

    override suspend fun getLastKnownLocation(): LocationInfo? {
        if (lastKnownLocation == null) {
            lastKnownLocation = requestCurrentLocation()
        }

        return lastKnownLocation
    }

    @SuppressLint("MissingPermission")
    override suspend fun requestCurrentLocation(): LocationInfo? {
        val cancellationTokenSource = CancellationTokenSource()

        return try {
            fusedLocationProvider.getCurrentLocation(
                QUALITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).await(cancellationTokenSource).toLocationInfo()
        } catch (c: CancellationException) {
            throw c
        } catch (e: Exception) {
            logD(e.message.toString())
            null
        } finally {
            cancellationTokenSource.cancel()
        }
    }

    companion object {

        private val highPrecisionLowIntervalRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setMinUpdateDistanceMeters(0f)
                .setMinUpdateIntervalMillis(1000)
                .build()

        private fun Location?.toLocationInfo(): LocationInfo? {
            val location = this
            return when {
                location != null -> LocationInfo(
                    latLng = LatLng(location.latitude, location.longitude),
                    bearing = location.bearing,
                    speed = when {
                        location.hasSpeed() -> location.speed.toKilometersPerHour()
                        else -> 0
                    }
                )
                else -> null
            }
        }
    }
}
