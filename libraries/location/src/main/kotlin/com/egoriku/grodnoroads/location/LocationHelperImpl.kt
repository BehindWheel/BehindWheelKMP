package com.egoriku.grodnoroads.location

import android.content.Context
import android.os.Looper
import androidx.core.location.LocationRequestCompat.QUALITY_HIGH_ACCURACY
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.location.MetricUtils.speedToKilometerPerHour
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

internal class LocationHelperImpl(context: Context) : LocationHelper {

    private val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

    private var lastKnownLocation: LocationInfo? = null

    override val lastLocationFlow = MutableStateFlow<LocationInfo?>(null)

    private val locationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation ?: return
            logD("locationCallback: ${location.latitude}, ${location.longitude}, ${location.bearing}")

            lastLocationFlow.tryEmit(
                LocationInfo(
                    latLng = LatLng(location.latitude, location.longitude),
                    bearing = location.bearing,
                    speed = when {
                        location.hasSpeed() -> speedToKilometerPerHour(location.speed)
                        else -> 0
                    }
                )
            )
        }
    }

    override fun startLocationUpdates() {
        fusedLocationProvider.removeLocationUpdates(locationCallback)
        fusedLocationProvider.requestLocationUpdates(
            highPrecisionLowIntervalRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    override fun stopLocationUpdates() {
        fusedLocationProvider.removeLocationUpdates(locationCallback)
    }

    override suspend fun getLastKnownLocation(): LocationInfo? {
        if (lastKnownLocation == null) {
            val cancellationTokenSource = CancellationTokenSource()
            val result = runCatching {
                fusedLocationProvider.getCurrentLocation(
                    QUALITY_HIGH_ACCURACY,
                    cancellationTokenSource.token
                ).await()
            }.onFailure {
                logD(it.message.toString())
            }

            val location = result.getOrNull()

            lastKnownLocation = when {
                location != null -> LocationInfo(
                    latLng = LatLng(location.latitude, location.longitude),
                    bearing = location.bearing,
                    speed = 0
                )
                else -> null
            }
        }

        return lastKnownLocation
    }

    companion object {
        private val highPrecisionLowIntervalRequest = LocationRequest.create()
            .apply {
                interval = 1000
                fastestInterval = 1000
                priority = Priority.PRIORITY_HIGH_ACCURACY
            }
    }
}