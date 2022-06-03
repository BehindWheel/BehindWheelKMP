package com.egoriku.grodnoroads.screen.settings.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Pref
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Pref.*
import com.egoriku.grodnoroads.screen.settings.domain.Theme
import com.egoriku.grodnoroads.screen.settings.domain.Theme.Companion.fromOrdinal
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.*
import com.egoriku.grodnoroads.screen.settings.store.preferences.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private const val DEFAULT_ALERT_DISTANCE_RADIUS = 600

interface SettingsStore : Store<Intent, State, Label>

class SettingsStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    sealed interface Intent {
        data class OnCheckedChanged(val preference: Pref) : Intent
        data class ProcessPreferenceClick(val preference: Pref) : Intent
        data class ProcessDialogResult(val preference: Pref) : Intent
        object CloseDialog : Intent
    }

    private sealed interface Message {
        data class NewSettings(val settingsState: SettingsState) : Message
        data class Dialog(val dialogState: DialogState) : Message
    }

    sealed interface Label

    sealed interface DialogState {
        data class ThemeDialog(val preference: AppTheme) : DialogState
        object None : DialogState
    }

    data class SettingsState(
        val appTheme: AppTheme = AppTheme(),
        val dialogState: DialogState = DialogState.None,
        val stationaryCameras: StationaryCameras = StationaryCameras(),
        val mobileCameras: MobileCameras = MobileCameras(),
        val trafficPolice: TrafficPolice = TrafficPolice(),
        val incidents: Incidents = Incidents(),

        val alertDistanceRadius: Int = DEFAULT_ALERT_DISTANCE_RADIUS
    )

    data class State(
        val settingsState: SettingsState = SettingsState(),
        val dialogState: DialogState = DialogState.None
    )

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): SettingsStore =
        object : SettingsStore, Store<Intent, State, Label> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dataStore.data
                            .map { preferences ->
                                SettingsState(
                                    stationaryCameras = StationaryCameras(
                                        isShow = preferences[IS_SHOW_STATIONARY_CAMERAS] ?: true
                                    ),
                                    mobileCameras = MobileCameras(
                                        isShow = preferences[IS_SHOW_MOBILE_CAMERAS] ?: true
                                    ),
                                    trafficPolice = TrafficPolice(
                                        isShow = preferences[IS_SHOW_TRAFFIC_POLICE_EVENTS] ?: true
                                    ),
                                    incidents = Incidents(
                                        isShow = preferences[IS_SHOW_INCIDENT_EVENTS] ?: true
                                    ),
                                    appTheme = AppTheme(
                                        current = fromOrdinal(
                                            preferences[APP_THEME] ?: Theme.System.theme
                                        )
                                    ),
                                    alertDistanceRadius = preferences[ALERT_DISTANCE]
                                        ?: DEFAULT_ALERT_DISTANCE_RADIUS
                                )
                            }.collect {
                                dispatch(Message.NewSettings(it))
                            }
                    }
                }
                onIntent<Intent.OnCheckedChanged> { onCheckedChanged ->
                    val preference = onCheckedChanged.preference

                    launch {
                        val key = when (preference) {
                            is StationaryCameras -> IS_SHOW_STATIONARY_CAMERAS
                            is MobileCameras -> IS_SHOW_MOBILE_CAMERAS
                            is TrafficPolice -> IS_SHOW_TRAFFIC_POLICE_EVENTS
                            is Incidents -> IS_SHOW_INCIDENT_EVENTS
                            else -> throw IllegalArgumentException(onCheckedChanged.toString())
                        }
                        val value = when (preference) {
                            is StationaryCameras -> preference.isShow
                            is MobileCameras -> preference.isShow
                            is TrafficPolice -> preference.isShow
                            is Incidents -> preference.isShow
                            else -> throw IllegalArgumentException(onCheckedChanged.toString())
                        }
                        dataStore.edit { it[key] = value }
                    }
                }
                onIntent<Intent.ProcessPreferenceClick> {
                    when (it.preference) {
                        is AppTheme -> {
                            dispatch(Message.Dialog(dialogState = DialogState.ThemeDialog(it.preference)))
                        }
                        else -> throw IllegalArgumentException(it.preference.toString())
                    }
                }
                onIntent<Intent.ProcessDialogResult> { dialogResult ->
                    dispatch(Message.Dialog(dialogState = DialogState.None))
                    when (dialogResult.preference) {
                        is AppTheme -> {
                            launch {
                                dataStore.edit {
                                    it[APP_THEME] = dialogResult.preference.current.theme
                                }
                            }
                        }
                        else -> throw IllegalArgumentException(dialogResult.preference.toString())
                    }
                }
                onIntent<Intent.CloseDialog> {
                    dispatch(Message.Dialog(dialogState = DialogState.None))
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.NewSettings -> copy(settingsState = message.settingsState)
                    is Message.Dialog -> copy(dialogState = message.dialogState)
                }
            }
        ) {}
}
