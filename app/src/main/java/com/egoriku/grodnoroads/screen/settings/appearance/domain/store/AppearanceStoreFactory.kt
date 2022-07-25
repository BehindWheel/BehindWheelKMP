package com.egoriku.grodnoroads.screen.settings.appearance.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.common.datastore.APP_LANGUAGE
import com.egoriku.grodnoroads.common.datastore.APP_THEME
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppLanguage
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppTheme
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearanceState
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Language
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Theme
import com.egoriku.grodnoroads.screen.settings.appearance.domain.store.AppearanceStore.*
import com.egoriku.grodnoroads.screen.settings.appearance.domain.store.AppearanceStore.Intent.CloseDialog
import com.egoriku.grodnoroads.screen.settings.appearance.domain.store.AppearanceStore.Intent.Modify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AppearanceStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AppearanceStore = object : AppearanceStore,
        Store<Intent, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dataStore.data
                            .map { preferences ->
                                AppearanceState(
                                    appTheme = AppTheme(
                                        current = Theme.fromOrdinal(
                                            preferences[APP_THEME] ?: Theme.System.theme
                                        )
                                    ),
                                    appLanguage = AppLanguage(
                                        current = Language.localeToLanguage(
                                            preferences[APP_LANGUAGE] ?: Language.Russian.lang
                                        )
                                    )
                                )
                            }.collect {
                                dispatch(Message.NewSettings(it))
                            }
                    }
                }
                onIntent<CloseDialog> {
                    dispatch(Message.Dialog(dialogState = AppearanceDialogState.None))
                }
                onIntent<Modify> {
                    when (it.preference) {
                        is AppTheme -> {
                            dispatch(
                                Message.Dialog(
                                    dialogState = AppearanceDialogState.ThemeDialogState(
                                        it.preference
                                    )
                                )
                            )
                        }
                        is AppLanguage -> {
                            dispatch(
                                Message.Dialog(
                                    dialogState = AppearanceDialogState.LanguageDialogState(
                                        it.preference
                                    )
                                )
                            )
                        }
                    }
                }
                onIntent<Intent.Update> { dialogResult ->
                    dispatch(Message.Dialog(dialogState = AppearanceDialogState.None))

                    when (dialogResult.preference) {
                        is AppTheme -> {
                            launch {
                                dataStore.edit {
                                    it[APP_THEME] = dialogResult.preference.current.theme
                                }
                            }
                        }
                        is AppLanguage -> {
                            launch {
                                dataStore.edit {
                                    it[APP_LANGUAGE] = dialogResult.preference.current.lang
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
                }
            }
        ) {}
}