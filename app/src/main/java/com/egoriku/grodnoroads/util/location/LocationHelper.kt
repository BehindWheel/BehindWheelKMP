package com.egoriku.grodnoroads.util.location

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.egoriku.grodnoroads.domain.model.UserPosition
import com.egoriku.grodnoroads.extension.logD
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface LocationHelper {
    val lastLocation: StateFlow<UserPosition>

    fun startLocationUpdates()
    fun stopLocationUpdates()
}

internal class LocationHelperImpl(context: Context) : LocationHelper {

    private val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)

    override val lastLocation: MutableStateFlow<UserPosition> = MutableStateFlow(UserPosition.None)

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            logD("locationCallback: ${location.latitude}, ${location.longitude}, ${location.bearing}")

            lastLocation.tryEmit(
                UserPosition(
                    latLng = LatLng(location.latitude, location.longitude),
                    bearing = location.bearing,
                    speed = if (location.hasSpeed()) location.speed * 18.0 / 5.0 else 0.0
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
            smallestDisplacement = 1.0f
        }
    }
}