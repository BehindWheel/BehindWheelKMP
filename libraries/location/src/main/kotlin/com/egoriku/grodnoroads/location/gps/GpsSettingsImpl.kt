package com.egoriku.grodnoroads.location.gps

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.location.LocationManagerCompat
import com.egoriku.grodnoroads.extensions.locationManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

internal class GpsSettingsImpl(context: Context) : GpsSettings {

    private val locationManager = context.locationManager

    override val gpsSettingState: GpsSettingState
        get() = when {
            LocationManagerCompat.isLocationEnabled(locationManager) -> GpsSettingState.Enabled
            else -> GpsSettingState.Disabled
        }

    @RequiresPermission(ACCESS_FINE_LOCATION)
    override fun fetchGpsSettingsUpdates() = callbackFlow {
        val gnssStatusCompat = GnssStatusCompat(
            locationManager = locationManager,
            onGpsStarted = {
                trySend(GpsSettingState.Enabled)
            },
            onGpsStopped = {
                trySend(GpsSettingState.Disabled)
            }
        )

        gnssStatusCompat.registerCallback()

        awaitClose { gnssStatusCompat.unregisterCallback() }
    }
}