package com.egoriku.grodnoroads.location

import kotlinx.coroutines.flow.StateFlow

interface LocationHelper {
    val lastLocationFlow: StateFlow<LocationInfo?>

    suspend fun getLastKnownLocation(): LocationInfo?

    suspend fun requestCurrentLocation(): LocationInfo?

    fun startLocationUpdates()
    fun stopLocationUpdates()
}