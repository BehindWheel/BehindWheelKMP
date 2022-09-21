package com.egoriku.grodnoroads.screen.map.domain.model

import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity.City.Grodno
import com.google.android.gms.maps.model.LatLng

data class LocationState(
    val latLng: LatLng,
    val bearing: Float,
    val speed: Int
) {

    companion object {
        val None = LocationState(
            latLng = LatLng(Grodno.latLng.latitude, Grodno.latLng.longitude),
            bearing = 0f,
            speed = 0
        )
    }
}