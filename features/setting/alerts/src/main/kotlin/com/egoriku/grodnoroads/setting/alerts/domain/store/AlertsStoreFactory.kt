package com.egoriku.grodnoroads.setting.alerts.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.setting.alerts.domain.component.AlertsComponent
import com.egoriku.grodnoroads.setting.alerts.domain.component.AlertsComponent.AlertSettingsState.AlertDistance
import com.egoriku.grodnoroads.setting.alerts.domain.store.AlertsStore.Message
import com.egoriku.grodnoroads.setting.alerts.domain.store.AlertsStore.State
import com.egoriku.grodnoroads.shared.appsettings.types.alert.alertDistance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

internal class AlertsStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {
    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AlertsStore = object : AlertsStore,
        Store<Nothing, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    dataStore.data
                        .map { preferences ->
                            AlertsComponent.AlertSettingsState(
                                alertDistance = AlertDistance(preferences.alertDistance)
                            )
                        }
                        .distinctUntilChanged()
                        .onEach {
                            dispatch(Message.NewSettings(it))
                        }
                        .launchIn(this)
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