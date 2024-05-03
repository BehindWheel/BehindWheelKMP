package com.egoriku.grodnoroads.settings.appearance.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.LanguageDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.None
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.ThemeDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppLanguage
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppTheme
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.KeepScreenOn
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceState
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.Intent
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.Intent.CloseDialog
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.Intent.Modify
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.Intent.Update
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.Message
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.State
import com.egoriku.grodnoroads.settings.appearance.domain.util.getCurrentLanguage
import com.egoriku.grodnoroads.settings.appearance.domain.util.resetAppLanguage
import com.egoriku.grodnoroads.settings.appearance.domain.util.setAppLanguage
import com.egoriku.grodnoroads.shared.persistent.appearance.Language
import com.egoriku.grodnoroads.shared.persistent.appearance.appTheme
import com.egoriku.grodnoroads.shared.persistent.appearance.keepScreenOn
import com.egoriku.grodnoroads.shared.persistent.appearance.updateAppTheme
import com.egoriku.grodnoroads.shared.persistent.appearance.updateKeepScreenOn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AppearanceStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    fun create(): AppearanceStore = object : AppearanceStore,
        Store<Intent, State, Nothing> by storeFactory.create(initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    dataStore.data
                        .map { preferences ->
                            AppearanceState(
                                appTheme = AppTheme(current = preferences.appTheme),
                                appLanguage = AppLanguage(
                                    current = getCurrentLanguage()
                                ),
                                keepScreenOn = KeepScreenOn(enabled = preferences.keepScreenOn)
                            )
                        }
                        .distinctUntilChanged()
                        .onEach { dispatch(Message.NewSettings(it)) }
                        .launchIn(this)
                }
                onIntent<CloseDialog> {
                    dispatch(Message.Dialog(dialogState = None))
                }
                onIntent<Modify> { modify ->
                    when (modify.preference) {
                        is AppTheme -> {
                            dispatch(
                                Message.Dialog(dialogState = ThemeDialogState(modify.preference))
                            )
                        }
                        is AppLanguage -> {
                            dispatch(
                                Message.Dialog(dialogState = LanguageDialogState(modify.preference))
                            )
                        }
                        is KeepScreenOn -> error("Not supported")
                    }
                }
                onIntent<Update> { update ->
                    when (update.preference) {
                        is AppTheme -> {
                            launch {
                                dataStore.edit {
                                    updateAppTheme(update.preference.current.theme)
                                }
                            }
                        }
                        is AppLanguage -> {
                            val language = update.preference.current

                            when (language) {
                                Language.System -> resetAppLanguage()
                                else -> setAppLanguage(language.lang)
                            }

                            dispatch(Message.UpdateLanguage(AppLanguage(current = language)))
                        }
                        is KeepScreenOn -> {
                            launch {
                                dataStore.edit {
                                    updateKeepScreenOn(update.preference.enabled)
                                }
                            }
                        }
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.NewSettings -> copy(appearanceState = message.appearanceState)
                    is Message.Dialog -> copy(dialogState = message.dialogState)
                    is Message.UpdateLanguage -> copy(
                        appearanceState = appearanceState.copy(
                            appLanguage = message.appLanguage
                        )
                    )
                }
            }) {}
}