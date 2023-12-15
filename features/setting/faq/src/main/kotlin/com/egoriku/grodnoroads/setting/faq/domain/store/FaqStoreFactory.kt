package com.egoriku.grodnoroads.setting.faq.domain.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.crashlytics.CrashlyticsTracker
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.setting.faq.domain.repository.FaqRepository
import com.egoriku.grodnoroads.setting.faq.domain.store.FaqStore.Message
import com.egoriku.grodnoroads.setting.faq.domain.store.FaqStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class FaqStoreFactory(
    private val storeFactory: StoreFactory,
    private val faqRepository: FaqRepository,
    private val crashlyticsTracker: CrashlyticsTracker
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
                            is ResultOf.Success -> dispatch(Message.Success(result.value))
                            is ResultOf.Failure -> crashlyticsTracker.recordException(result.throwable)
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