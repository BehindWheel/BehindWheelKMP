package com.egoriku.grodnoroads.screen.root.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory.State
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme
import com.egoriku.grodnoroads.shared.persistent.appearance.appTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

interface RootStore : Store<Nothing, State, Nothing>

class RootStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    sealed interface Message {
        data class NewState(val state: State) : Message
    }

    data class State(val theme: Theme? = null)

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): RootStore =
        object : RootStore, Store<Nothing, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    dataStore.data
                        .map { preferences ->
                            State(theme = Theme.fromOrdinal(preferences.appTheme.theme))
                        }
                        .distinctUntilChanged()
                        .onEach { dispatch(Message.NewState(state = it)) }
                        .launchIn(this)

                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.NewState -> copy(theme = message.state.theme)
                }
            }
        ) {}
}
