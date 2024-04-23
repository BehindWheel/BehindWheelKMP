package com.egoriku.grodnoroads.quicksettings.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.quicksettings.domain.model.QuickSettingsState
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.AppTheme
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.MarkerFiltering
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.TrafficJamOnMap
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.VoiceAlerts
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsStore.Intent
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsStore.Intent.Update
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsStore.Message
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsStore.Message.NewSettings
import com.egoriku.grodnoroads.shared.persistent.alert.alertsVoiceAlertEnabled
import com.egoriku.grodnoroads.shared.persistent.alert.updateAlertsVoiceAlertAvailability
import com.egoriku.grodnoroads.shared.persistent.appearance.appTheme
import com.egoriku.grodnoroads.shared.persistent.appearance.updateAppTheme
import com.egoriku.grodnoroads.shared.persistent.map.filtering.filteringMarkers
import com.egoriku.grodnoroads.shared.persistent.map.filtering.updateFiltering
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.trafficJamOnMap
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.updateTrafficJamAppearance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class QuickSettingsStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): QuickSettingsStore =
        object : QuickSettingsStore,
            Store<Intent, QuickSettingsState, Nothing> by storeFactory.create(
                initialState = QuickSettingsState(),
                executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                    onAction<Unit> {
                        dataStore.data
                            .map { preferences ->
                                QuickSettingsState(
                                    appTheme = AppTheme(current = preferences.appTheme),
                                    markerFiltering = MarkerFiltering(current = preferences.filteringMarkers),
                                    trafficJamOnMap = TrafficJamOnMap(isShow = preferences.trafficJamOnMap),
                                    voiceAlerts = VoiceAlerts(enabled = preferences.alertsVoiceAlertEnabled)
                                )
                            }
                            .distinctUntilChanged()
                            .onEach { dispatch(NewSettings(it)) }
                            .launchIn(this)
                    }
                    onIntent<Update> {
                        launch {
                            dataStore.edit {
                                when (val pref = it.preference) {
                                    is AppTheme -> updateAppTheme(pref.current.theme)
                                    is MarkerFiltering -> updateFiltering(pref.current)
                                    is TrafficJamOnMap -> updateTrafficJamAppearance(pref.isShow)
                                    is VoiceAlerts -> updateAlertsVoiceAlertAvailability(pref.enabled)
                                }
                            }
                        }
                    }
                },
                bootstrapper = SimpleBootstrapper(Unit),
                reducer = { message: Message ->
                    when (message) {
                        is NewSettings -> message.appearanceState
                    }
                }
            ) {}
}
