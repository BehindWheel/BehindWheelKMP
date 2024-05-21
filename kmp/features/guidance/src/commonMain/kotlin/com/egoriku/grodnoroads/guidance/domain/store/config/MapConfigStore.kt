package com.egoriku.grodnoroads.guidance.domain.store.config

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.guidance.domain.model.AppMode
import com.egoriku.grodnoroads.guidance.domain.model.MapInternalConfig
import com.egoriku.grodnoroads.guidance.domain.model.area.Area
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStore.Intent
import com.egoriku.grodnoroads.guidance.domain.store.config.MapConfigStore.StoreState
import com.egoriku.grodnoroads.location.LatLng

internal interface MapConfigStore : Store<Intent, StoreState, Nothing> {

    sealed interface Intent {
        data class CheckLocation(val latLng: LatLng) : Intent
        data object StartDriveMode : Intent
        data object StopDriveMode : Intent

        sealed interface ChooseLocation {
            data object OpenChooseLocation : Intent
            data class UserMapZoom(val zoom: Float) : Intent
            data object CancelChooseLocation : Intent
        }
    }

    data class StoreState(
        val mapInternalConfig: MapInternalConfig = MapInternalConfig.EMPTY,
        val areas: List<Area> = emptyList(),
        val zoomLevel: Float = 12.5f,
        val userZoomLevel: Float = 0f,
        val alertRadius: Int = 0,
        val currentAppMode: AppMode = AppMode.Default,
        val isChooseInDriveMode: Boolean = false
    )
}