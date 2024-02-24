package com.egoriku.grodnoroads.map.domain.store.quickactions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.map.domain.store.quickactions.QuickActionsStore.QuickActionsIntent
import com.egoriku.grodnoroads.map.domain.store.quickactions.QuickActionsStore.QuickActionsIntent.Update
import com.egoriku.grodnoroads.map.domain.store.quickactions.QuickActionsStore.QuickActionsMessage
import com.egoriku.grodnoroads.map.domain.store.quickactions.QuickActionsStore.QuickActionsMessage.NewSettings
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsPref.*
import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsState
import com.egoriku.grodnoroads.shared.appsettings.extension.edit
import com.egoriku.grodnoroads.shared.appsettings.types.alert.alertsVoiceAlertEnabled
import com.egoriku.grodnoroads.shared.appsettings.types.alert.updateAlertsVoiceAlertAvailability
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.appTheme
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.updateAppTheme
import com.egoriku.grodnoroads.shared.appsettings.types.map.filtering.filteringMarkers
import com.egoriku.grodnoroads.shared.appsettings.types.map.filtering.updateFiltering
import com.egoriku.grodnoroads.shared.appsettings.types.map.mapstyle.trafficJamOnMap
import com.egoriku.grodnoroads.shared.appsettings.types.map.mapstyle.updateTrafficJamAppearance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class QuickActionsStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): QuickActionsStore =
        object : QuickActionsStore,
            Store<QuickActionsIntent, QuickActionsState, Nothing> by storeFactory.create(
                initialState = QuickActionsState(),
                executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                    onAction<Unit> {
                        dataStore.data
                            .map { preferences ->
                                QuickActionsState(
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
                reducer = { message: QuickActionsMessage ->
                    when (message) {
                        is NewSettings -> message.appearanceState
                    }
                }
            ) {}
}
