package com.egoriku.grodnoroads.screen.settings.whatsnew.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.screen.settings.whatsnew.data.WhatsNewRepository
import com.egoriku.grodnoroads.screen.settings.whatsnew.store.WhatsNewStore.Message
import com.egoriku.grodnoroads.screen.settings.whatsnew.store.WhatsNewStore.State
import com.egoriku.grodnoroads.screen.settings.whatsnew.store.WhatsNewStore.State.ReleaseNotes
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WhatsNewStoreFactory(
    private val storeFactory: StoreFactory,
    private val whatsNewRepository: WhatsNewRepository
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
                            is ResultOf.Success -> {
                                dispatch(
                                    Message.Success(
                                        releaseNotes = result.value.map {
                                            ReleaseNotes(
                                                version = it.version,
                                                notes = it.notes.replace("\\n", "\n")
                                            )
                                        }
                                    )
                                )
                            }
                            is ResultOf.Failure -> Firebase.crashlytics.recordException(result.exception)
                                .also {
                                    logD(result.exception.message.toString())
                                }
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