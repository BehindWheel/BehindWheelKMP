package com.egoriku.grodnoroads.location.gps

import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.flow.Flow

interface GpsSettings {

    val gpsSettingState: GpsSettingState

    @RequiresPermission(ACCESS_FINE_LOCATION)
    fun fetchGpsSettingsUpdates(): Flow<GpsSettingState>
}