package com.egoriku.grodnoroads.screen.root.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory.State
import com.egoriku.grodnoroads.screen.settings.domain.Theme
import com.egoriku.grodnoroads.screen.settings.store.preferences.APP_THEME
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

interface RootStore : Store<Nothing, State, Nothing>

class RootStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    sealed interface Message {
        data class NewTheme(val theme: Theme) : Message
    }

    data class State(val theme: Theme = Theme.System)

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): RootStore =
        object : RootStore, Store<Nothing, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dataStore.data
                            .map { preferences ->
                                Theme.fromOrdinal(preferences[APP_THEME] ?: Theme.System.theme)
                            }
                            .collect {
                                dispatch(Message.NewTheme(theme = it))
                            }
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.NewTheme -> copy(theme = message.theme)
                }
            }
        ) {}
}
