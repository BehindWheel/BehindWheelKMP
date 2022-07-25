package com.egoriku.grodnoroads.screen.settings.alerts.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.ALERT_DISTANCE
import com.egoriku.grodnoroads.screen.settings.alerts.domain.component.AlertsComponent
import com.egoriku.grodnoroads.screen.settings.alerts.domain.store.AlertsStore.Message
import com.egoriku.grodnoroads.screen.settings.alerts.domain.store.AlertsStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AlertsStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {
    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AlertsStore = object : AlertsStore,
        Store<Nothing, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dataStore.data
                            .map { preferences ->
                                AlertsComponent.AlertSettingsState(
                                    alertDistance = AlertsComponent.AlertSettingsState.AlertDistance(
                                        preferences[ALERT_DISTANCE] ?: 600
                                    )
                                )
                            }.collect {
                                dispatch(Message.NewSettings(it))
                            }
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.NewSettings -> copy(alertDistance = message.alertSettingsState)
                }
            }
        ) {}
}