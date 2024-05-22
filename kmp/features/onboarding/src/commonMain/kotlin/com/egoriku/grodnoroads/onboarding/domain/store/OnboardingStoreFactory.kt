package com.egoriku.grodnoroads.onboarding.domain.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.datastore.edit
import com.egoriku.grodnoroads.onboarding.domain.component.OnboardingComponent.OnboardingPref.DefaultCity
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore.Intent.CompleteOnboarding
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore.Intent.Modify
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore.Label
import com.egoriku.grodnoroads.onboarding.domain.store.OnboardingStore.Message
import com.egoriku.grodnoroads.shared.persistent.map.location.defaultCity
import com.egoriku.grodnoroads.shared.persistent.map.location.updateDefaultCity
import com.egoriku.grodnoroads.shared.persistent.onboarding.completeOnboarding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class OnboardingStoreFactory(
    private val storeFactory: StoreFactory,
    private val dataStore: DataStore<Preferences>
) {

    fun create(): OnboardingStore = object : OnboardingStore,
        Store<OnboardingStore.Intent, OnboardingStore.State, Label> by storeFactory.create(
            initialState = OnboardingStore.State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    dataStore.data
                        .map { it.defaultCity }
                        .distinctUntilChanged()
                        .onEach {
                            dispatch(Message.OnUpdateCity(it))
                        }
                }
                onIntent<CompleteOnboarding> {
                    launch {
                        dataStore.edit {
                            completeOnboarding()
                        }
                        publish(Label.FinishOnboarding)
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