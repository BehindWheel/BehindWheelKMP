package com.egoriku.grodnoroads.shared.geolocation

import kotlinx.coroutines.flow.Flow

interface LocationService {

    fun locationUpdates(): Flow<LocationInfo?>

    suspend fun getLastKnownLocation(): LocationInfo?

    suspend fun requestCurrentLocation(): LocationInfo?
}
