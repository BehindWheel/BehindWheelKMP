package com.egoriku.grodnoroads.screen.map.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.screen.map.domain.model.MapConfig
import com.egoriku.grodnoroads.screen.map.domain.store.MapConfigStore.Intent
import com.egoriku.grodnoroads.screen.map.domain.store.MapConfigStore.StoreState
import com.google.android.gms.maps.model.LatLng

interface MapConfigStore : Store<Intent, StoreState, Nothing> {

    sealed interface Intent {
        data class CheckLocation(val latLng: LatLng): Intent
    }
    data class StoreState(
        val mapConfig: MapConfig = MapConfig.EMPTY
    )

    sealed interface Message {
        data class OnMapConfig(val mapConfig: MapConfig) : Message
    }
}