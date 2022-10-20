package com.egoriku.grodnoroads.map.domain.store.config

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.map.domain.model.MapInternalConfig
import com.egoriku.grodnoroads.map.domain.store.config.MapConfigStore.Intent
import com.egoriku.grodnoroads.map.domain.store.config.MapConfigStore.StoreState
import com.google.android.gms.maps.model.LatLng

internal interface MapConfigStore : Store<Intent, StoreState, Nothing> {

    sealed interface Intent {
        data class CheckLocation(val latLng: LatLng) : Intent
    }

    data class StoreState(
        val mapInternalConfig: MapInternalConfig = MapInternalConfig.EMPTY,
        val zoomLevelDriveMode: Float = -1f
    )

    sealed interface Message {
        data class OnMapConfigInternal(val mapConfig: MapInternalConfig) : Message
        data class OnZoomLevel(val zoomLevel: Float) : Message
    }
}