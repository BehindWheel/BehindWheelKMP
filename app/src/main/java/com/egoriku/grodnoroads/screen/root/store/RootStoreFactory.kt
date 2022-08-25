package com.egoriku.grodnoroads.screen.root.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.APP_LANGUAGE
import com.egoriku.grodnoroads.common.datastore.PreferenceKeys.APP_THEME
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory.Intent
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory.Intent.CloseDialog
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory.Message.UpdateHeadLampDialog
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory.State
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampDispatcher
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Language
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

interface RootStore : Store<Intent, State, Nothing>

class RootStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    sealed interface Intent {
        object CloseDialog : Intent
    }

    sealed interface Message {
        data class NewState(val state: State) : Message
        data class UpdateHeadLampDialog(val headLampType: HeadLampType) : Message
    }

    data class State(
        val theme: Theme = Theme.System,
        val language: Language = Language.Russian,
        val headLampType: HeadLampType = HeadLampType.None
    )

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): RootStore =
        object : RootStore, Store<Intent, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dataStore.data
                            .map { preferences ->
                                val value = preferences[APP_LANGUAGE] ?: Language.Russian.lang
                                State(
                                    theme = Theme.fromOrdinal(
                                        preferences[APP_THEME] ?: Theme.System.theme
                                    ),
                                    language = Language.localeToLanguage(value)
                                )
                            }
                            .collect {
                                dispatch(Message.NewState(state = it))
                            }
                    }
                    launch {
                        dispatch(UpdateHeadLampDialog(headLampType = HeadLampDispatcher.calculateType()))
                    }
                }
                onIntent<CloseDialog> {
                    launch {
                        dispatch(UpdateHeadLampDialog(headLampType = HeadLampType.None))
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.NewState -> copy(
                        theme = message.state.theme,
                        language = message.state.language,
                    )
                    is UpdateHeadLampDialog -> copy(headLampType = message.headLampType)
                }
            }
        ) {}
}
