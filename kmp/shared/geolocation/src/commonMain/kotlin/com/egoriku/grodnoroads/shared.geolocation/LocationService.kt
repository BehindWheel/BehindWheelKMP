package com.egoriku.grodnoroads.shared.geolocation

import kotlinx.coroutines.flow.StateFlow

interface LocationService {

    val lastLocationFlow: StateFlow<LocationInfo?>

    suspend fun getLastKnownLocation(): LocationInfo?

    suspend fun requestCurrentLocation(): LocationInfo?

    fun startLocationUpdates()
    fun stopLocationUpdates()
}
