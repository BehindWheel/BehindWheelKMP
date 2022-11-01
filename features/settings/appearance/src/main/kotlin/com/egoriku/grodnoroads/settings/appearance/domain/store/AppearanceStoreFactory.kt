package com.egoriku.grodnoroads.settings.appearance.domain.store

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppLanguage
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppTheme
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceState
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.*
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.Intent.CloseDialog
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.Intent.Modify
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Language
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.appTheme
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.updateAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AppearanceStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AppearanceStore = object : AppearanceStore,
        Store<Intent, State, Nothing> by storeFactory.create(initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dataStore.data.map { preferences ->
                            AppearanceState(
                                appTheme = AppTheme(current = preferences.appTheme),
                                appLanguage = AppLanguage(
                                    current = Language.localeToLanguage(AppCompatDelegate.getApplicationLocales()[0]?.language)
                                        ?: Language.English
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
                                    it.updateAppTheme(dialogResult.preference.current.theme)
                                }
                            }
                        }
                        is AppLanguage -> {
                            val language = dialogResult.preference.current

                            AppCompatDelegate.setApplicationLocales(
                                LocaleListCompat.forLanguageTags(language.lang)
                            )

                            dispatch(Message.UpdateLanguage(AppLanguage(current = language)))
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