package com.egoriku.grodnoroads.common.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.APP_THEME
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.DEFAULT_CITY
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.GOOGLE_MAP_STYLE
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_CAR_CRASH_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_INCIDENT_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_MOBILE_CAMERAS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_STATIONARY_CAMERAS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_TRAFFIC_JAM_APPEARANCE
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_TRAFFIC_JAM_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_TRAFFIC_POLICE_EVENTS
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.IS_SHOW_WILD_ANIMALS_EVENTS
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Language
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Theme
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.DefaultCity.City
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponent.MapPref.GoogleMapStyle.Style
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataFlow {
    val Context.appTheme: Flow<Theme>
        get() = dataStore.data.map { it.appTheme }

    val Context.appLanguage: Flow<Language>
        get() = dataStore.data.map { it.language }

    val Context.googleMapStyle: Flow<Style>
        get() = dataStore.data.map { it.googleMapStyle }

    val Context.trafficJamOnMap: Flow<Boolean>
        get() = dataStore.data.map { it.trafficJamOnMap }

    val Preferences.appTheme: Theme
        get() = Theme.fromOrdinal(this[APP_THEME] ?: Theme.System.theme)

    val Preferences.language: Language
        get() = Language.localeToLanguage(
            this[PreferenceKeys.APP_LANGUAGE] ?: Language.Russian.lang
        )

    val Preferences.defaultCity: City
        get() = City.toCity(this[DEFAULT_CITY] ?: City.Grodno.cityName)

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