package com.egoriku.grodnoroads.guidance.domain.store.mapevents

import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Camera.*
import com.egoriku.grodnoroads.guidance.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.guidance.domain.model.MapEventType
import com.egoriku.grodnoroads.guidance.domain.store.mapevents.MapEventsStore.Intent
import com.egoriku.grodnoroads.guidance.domain.store.mapevents.MapEventsStore.State
import com.egoriku.grodnoroads.location.LatLng
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.MediumSpeedCamera
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.MobileCamera
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Camera.StationaryCamera
import com.egoriku.grodnoroads.map.domain.model.MapEvent.Reports
import com.egoriku.grodnoroads.map.domain.store.mapevents.MapEventsStore.State
import com.egoriku.grodnoroads.shared.appsettings.types.map.filtering.Filtering

interface MapEventsStore : Store<Nothing, State, Nothing> {

    sealed interface Message {
        data class OnStationary(val data: List<StationaryCamera>) : Message
        data class OnMediumSpeed(val data: List<MediumSpeedCamera>) : Message
        data class OnNewReports(val data: List<Reports>) : Message
        data class OnMobileCamera(val data: List<MobileCamera>) : Message
        data class OnUserCount(val data: Int) : Message
        data class OnUpdateFilterTime(val time: Long) : Message
    }

    data class State(
        val stationaryCameras: List<StationaryCamera> = emptyList(),
        val mediumSpeedCameras: List<MediumSpeedCamera> = emptyList(),
        val mobileCameras: List<MobileCamera> = emptyList(),
        val reports: List<Reports> = emptyList(),
        val userCount: Int = 0,
        val filterEventsTime: Long = Filtering.Hours1.timeInMilliseconds
    )
}