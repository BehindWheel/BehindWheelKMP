package com.egoriku.grodnoroads.cityselector.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.cityselector.domain.component.CitySelectorComponent.CitySelectorPref.DefaultCity
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.Intent
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.Intent.CompleteIntro
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.Intent.Modify
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.Label
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.Message
import com.egoriku.grodnoroads.cityselector.domain.store.CitySelectorStore.State
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.shared.persistent.intro.showIntro
import com.egoriku.grodnoroads.shared.persistent.map.location.defaultCity
import com.egoriku.grodnoroads.shared.persistent.map.location.updateDefaultCity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class CitySelectorStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    fun create(): CitySelectorStore = object :
        CitySelectorStore,
        Store<Intent, State, Label> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    dataStore.data
                        .map { it.defaultCity }
                        .distinctUntilChanged()
                        .onEach {
                            dispatch(Message.OnUpdateCity(it))
                        }
                }
                onIntent<CompleteIntro> {
                    launch {
                        dataStore.edit {
                            showIntro(false)
                        }
                        publish(Label.FinishIntro)
                    }
                }
                onIntent<Modify> {
                    val preference = it.preference

                    launch {
                        dataStore.edit {
                            when (preference) {
                                is DefaultCity -> updateDefaultCity(preference.current.cityName)
                            }
                        }
                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.OnUpdateCity -> copy(defaultCity = defaultCity.copy(current = message.city))
                }
            }
        ) {}
}
