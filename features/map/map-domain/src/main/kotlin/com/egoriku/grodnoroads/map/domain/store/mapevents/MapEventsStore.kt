package com.egoriku.grodnoroads.map.domain.store.mapevents

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.map.domain.model.MapEvent
import com.egoriku.grodnoroads.map.domain.model.MapEventType
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStore.Intent
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStore.State
import com.google.android.gms.maps.model.LatLng

interface MapEventsStore : Store<Intent, State, Nothing> {

    sealed interface Intent {
        data class ReportAction(val params: Params) : Intent {
            data class Params(
                val latLng: LatLng,
                val mapEventType: MapEventType,
                val shortMessage: String,
                val message: String
            )
        }
    }

    sealed interface Message {
        data class OnStationary(val data: List<MapEvent.StationaryCamera>) : Message
        data class OnNewReports(val data: List<MapEvent.Reports>) : Message
        data class OnMobileCamera(val data: List<MapEvent.MobileCamera>) : Message
    }

    data class State(
        val stationaryCameras: List<MapEvent.StationaryCamera> = emptyList(),
        val reports: List<MapEvent.Reports> = emptyList(),
        val mobileCamera: List<MapEvent.MobileCamera> = emptyList()
    )
}