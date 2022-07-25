package com.egoriku.grodnoroads.common.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.GOOGLE_MAP_STYLE
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_CAR_CRASH_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_INCIDENT_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_MOBILE_CAMERAS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_STATIONARY_CAMERAS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_TRAFFIC_JAM_APPEARANCE
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_TRAFFIC_JAM_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_TRAFFIC_POLICE_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_WILD_ANIMALS_EVENTS
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.GoogleMapStyle.Style
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataFlow {
    val Context.googleMapStyle: Flow<Style>
        get() = dataStore.data.map { it.googleMapStyle }

    val Context.trafficJamOnMap: Flow<Boolean>
        get() = dataStore.data.map { it.trafficJamOnMap }

    val Preferences.trafficJamOnMap: Boolean
        get() = this[IS_SHOW_TRAFFIC_JAM_APPEARANCE] ?: false

    val Preferences.googleMapStyle: Style
        get() = Style.values()
            .firstOrNull { it.type == this[GOOGLE_MAP_STYLE] }
            ?: Style.Minimal

    val Preferences.isShowStationaryCameras: Boolean
        get() = this[IS_SHOW_STATIONARY_CAMERAS] ?: true

    val Preferences.isShowMobileCameras: Boolean
        get() = this[IS_SHOW_MOBILE_CAMERAS] ?: true

    val Preferences.isShowTrafficPolice: Boolean
        get() = this[IS_SHOW_TRAFFIC_POLICE_EVENTS] ?: true

    val Preferences.isShowRoadIncidents: Boolean
        get() = this[IS_SHOW_INCIDENT_EVENTS] ?: true

    val Preferences.isShowCarCrash: Boolean
        get() = this[IS_SHOW_CAR_CRASH_EVENTS] ?: true

    val Preferences.isShowTrafficJam: Boolean
        get() = this[IS_SHOW_TRAFFIC_JAM_EVENTS] ?: true

    val Preferences.isShowWildAnimals: Boolean
        get() = this[IS_SHOW_WILD_ANIMALS_EVENTS] ?: true
}