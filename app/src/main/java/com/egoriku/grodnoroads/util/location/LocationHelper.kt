package com.egoriku.grodnoroads.util.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationRequest.QUALITY_HIGH_ACCURACY
import android.os.Looper
import com.egoriku.grodnoroads.extension.logD
import com.egoriku.grodnoroads.screen.map.domain.LocationState
import com.egoriku.grodnoroads.util.MetricUtils.speedToKilometerPerHour
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await

interface LocationHelper {
    val lastLocationFlow: StateFlow<LocationState>

    suspend fun getLastKnownLocation(): LocationState?

    fun startLocationUpdates()
    fun stopLocationUpdates()
}

internal class LocationHelperImpl(context: Context) : LocationHelper {

    private val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

    private var lastKnownLocation: LocationState? = null

    override val lastLocationFlow = MutableStateFlow(LocationState.None)

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation ?: return
            logD("locationCallback: ${location.latitude}, ${location.longitude}, ${location.bearing}")

            lastLocationFlow.tryEmit(
                LocationState(
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

    @SuppressLint("MissingPermission")
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

    override suspend fun getLastKnownLocation(): LocationState? {
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
                location != null -> LocationState(
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
        private val highPrecisionLowIntervalRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 1000
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }
    }
}