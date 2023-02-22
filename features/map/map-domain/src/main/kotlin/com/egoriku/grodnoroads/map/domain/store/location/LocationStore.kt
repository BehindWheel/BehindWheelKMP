package com.egoriku.grodnoroads.map.domain.store.location

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.map.domain.model.LastLocation
import com.egoriku.grodnoroads.map.domain.store.location.LocationStore.*
import com.google.android.gms.maps.model.LatLng

interface LocationStore : Store<Intent, State, Label> {

    sealed interface Intent {
        object StartLocationUpdates : Intent
        object StopLocationUpdates : Intent
        object DisabledLocation : Intent
        data class SetLocation(val latLng: LatLng) : Intent
    }

    sealed interface Message {
        data class OnNewLocation(val lastLocation: LastLocation) : Message
    }

    sealed interface Label {
        object None : Label
        data class NewLocation(val latLng: LatLng) : Label
        data class ShowToast(val resId: Int) : Label
    }

    data class State(
        val lastLocation: LastLocation = LastLocation.None
    )
}