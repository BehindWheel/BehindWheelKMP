package com.egoriku.grodnoroads.location

import kotlinx.coroutines.flow.StateFlow

interface LocationHelper {
    val lastLocationFlow: StateFlow<LocationInfo?>

    suspend fun getLastKnownLocation(): LocationInfo?

    fun startLocationUpdates()
    fun stopLocationUpdates()
}