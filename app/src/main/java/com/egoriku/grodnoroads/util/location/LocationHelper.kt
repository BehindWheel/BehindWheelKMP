package com.egoriku.grodnoroads.util.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.egoriku.grodnoroads.screen.map.domain.LocationState
import com.egoriku.grodnoroads.extension.logD
import com.egoriku.grodnoroads.util.MetricUtils.speedToKilometerPerHour
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface LocationHelper {
    val lastLocation: StateFlow<LocationState>

    fun startLocationUpdates()
    fun stopLocationUpdates()
}

internal class LocationHelperImpl(context: Context) : LocationHelper {

    private val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

    override val lastLocation: MutableStateFlow<LocationState> = MutableStateFlow(LocationState.None)

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            logD("locationCallback: ${location.latitude}, ${location.longitude}, ${location.bearing}")

            lastLocation.tryEmit(
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

    companion object {
        private val highPrecisionLowIntervalRequest = LocationRequest.create().apply {
            interval = 1000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
}