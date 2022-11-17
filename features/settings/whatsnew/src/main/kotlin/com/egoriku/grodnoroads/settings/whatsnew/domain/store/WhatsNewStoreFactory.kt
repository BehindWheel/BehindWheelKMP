package com.egoriku.grodnoroads.settings.whatsnew.domain.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.crashlytics.CrashlyticsTracker
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.settings.whatsnew.domain.repository.WhatsNewRepository
import com.egoriku.grodnoroads.settings.whatsnew.domain.store.WhatsNewStore.Message
import com.egoriku.grodnoroads.settings.whatsnew.domain.store.WhatsNewStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class WhatsNewStoreFactory(
    private val storeFactory: StoreFactory,
    private val whatsNewRepository: WhatsNewRepository,
    private val crashlyticsTracker: CrashlyticsTracker
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    internal fun create(): WhatsNewStore =
        object : WhatsNewStore, Store<Nothing, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dispatch(Message.Loading(true))

                        when (val result = whatsNewRepository.load()) {
                            is ResultOf.Success -> dispatch(Message.Success(releaseNotes = result.value))
                            is ResultOf.Failure -> crashlyticsTracker.recordException(result.exception)
                        }
                        dispatch(Message.Loading(false))

                    }
                }
            },
            bootstrapper = SimpleBootstrapper(Unit),
            reducer = { message: Message ->
                when (message) {
                    is Message.Loading -> copy(isLoading = message.isLoading)
                    is Message.Success -> copy(releaseNotes = message.releaseNotes)
                }
            }
        ) {}
}