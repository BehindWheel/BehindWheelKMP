package com.egoriku.grodnoroads.screen.settings.faq.domain.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.extensions.logD
import com.egoriku.grodnoroads.screen.settings.faq.data.FaqRepository
import com.egoriku.grodnoroads.screen.settings.faq.domain.store.FaqStore.Message
import com.egoriku.grodnoroads.screen.settings.faq.domain.store.FaqStore.State
import com.egoriku.grodnoroads.screen.settings.faq.domain.store.FaqStore.State.FAQ
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FaqStoreFactory(
    private val storeFactory: StoreFactory,
    private val faqRepository: FaqRepository
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    internal fun create(): FaqStore =
        object : FaqStore, Store<Nothing, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onAction<Unit> {
                    launch {
                        dispatch(Message.Loading(true))

                        when (val result = faqRepository.load()) {
                            is ResultOf.Success -> {
                                dispatch(
                                    Message.Success(
                                        faq = result.value.map {
                                            FAQ(
                                                question = it.question,
                                                answer = it.answer.replace("\\n", "\n")
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
                    is Message.Success -> copy(faq = message.faq)
                }
            }
        ) {}
}