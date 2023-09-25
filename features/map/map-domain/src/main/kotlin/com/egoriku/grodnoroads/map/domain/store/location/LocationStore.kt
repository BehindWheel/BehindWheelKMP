package com.egoriku.grodnoroads.map.domain.store.location

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore.*
import com.egoriku.grodnoroads.mapswrapper.core.StableLatLng

interface LocationStore : Store<Intent, State, Label> {

    sealed interface Intent {
        data object StartLocationUpdates : Intent
        data object StopLocationUpdates : Intent
        data object DisabledLocation : Intent
        data class SetUserLocation(val latLng: StableLatLng) : Intent
        data object InvalidateLocation : Intent
    }

    sealed interface Message {
        data class OnNewLocation(val lastLocation: LastLocation) : Message
        data class OnUserLocation(val lastLocation: LastLocation) : Message
    }

    sealed interface Label {
        data object None : Label
        data class NewLocation(val latLng: StableLatLng) : Label
        data class ShowToast(val resId: Int) : Label
    }

    data class State(
        val lastLocation: LastLocation = LastLocation.None,
        val userLocation: LastLocation = LastLocation.None,
    )
}