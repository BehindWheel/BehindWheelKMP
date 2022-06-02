package com.egoriku.grodnoroads.screen.settings

import com.egoriku.grodnoroads.screen.settings.domain.Theme
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.DialogState
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.SettingsState
import kotlinx.coroutines.flow.Flow

interface SettingsComponent {

    val settingsState: Flow<SettingsState>
    val dialogState: Flow<DialogState>

    fun onCheckedChanged(preference: Pref)

    fun process(preference: Pref)

    fun processResult(preference: Pref)

    fun closeDialog()

    sealed interface Pref {
        data class AppTheme(
            val current: Theme = Theme.System,
            val values: List<Theme> = listOf(Theme.System, Theme.Light, Theme.Dark)
        ) : Pref

        data class StationaryCameras(val isShow: Boolean = true) : Pref
        data class MobileCameras(val isShow: Boolean = true) : Pref
        data class TrafficPolice(val isShow: Boolean = true) : Pref
        data class Incidents(val isShow: Boolean = true) : Pref
    }
}