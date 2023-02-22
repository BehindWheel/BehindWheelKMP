package com.egoriku.grodnoroads.map.domain.store.config

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.map.domain.model.AppMode
import com.egoriku.grodnoroads.map.domain.model.MapInternalConfig
import com.egoriku.grodnoroads.map.domain.model.ReportType
import com.egoriku.grodnoroads.map.domain.store.config.MapConfigStore.Intent
import com.egoriku.grodnoroads.map.domain.store.config.MapConfigStore.StoreState
import com.google.android.gms.maps.model.LatLng

internal interface MapConfigStore : Store<Intent, StoreState, Nothing> {

    sealed interface Intent {
        data class CheckLocation(val latLng: LatLng) : Intent
        object StartDriveMode : Intent
        object StopDriveMode : Intent

        sealed interface ChooseLocation {
            data class OpenChooseLocation(val reportType: ReportType) : Intent
            object CancelChooseLocation : Intent
        }
    }

    data class StoreState(
        val mapInternalConfig: MapInternalConfig = MapInternalConfig.EMPTY,
        val zoomLevel: Float = 12.5f,
        val appMode: AppMode = AppMode.Default,
        val reportType: ReportType? = null
    )
}