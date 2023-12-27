package com.egoriku.grodnoroads.shared.geolocation

import com.egoriku.grodnoroads.coroutines.CStateFlow

interface LocationService {

    val lastLocationFlow: CStateFlow<LocationInfo?>

    suspend fun getLastKnownLocation(): LocationInfo?

    suspend fun requestCurrentLocation(): LocationInfo?

    fun startLocationUpdates()
    fun stopLocationUpdates()
}