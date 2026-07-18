package com.egoriku.grodnoroads.settings.changelog.domain.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.crashlytics.shared.CrashlyticsTracker
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.extensions.coroutines.smartJob
import com.egoriku.grodnoroads.settings.changelog.domain.model.ErrorType
import com.egoriku.grodnoroads.settings.changelog.domain.repository.ChangelogRepository
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.Intent
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.Message
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class ChangelogStoreFactory(
    private val storeFactory: StoreFactory,
    private val changelogRepository: ChangelogRepository,
    private val crashlyticsTracker: CrashlyticsTracker
) {

    internal fun create(): ChangelogStore = object :
        ChangelogStore,
        Store<Intent, State, Nothing> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                var loadJob by smartJob()

                onIntent<Intent.SelectPlatform> { intent ->
                    loadJob = launch {
                        dispatch(Message.PlatformUpdated(intent.platform))
                        dispatch(Message.Loading)

                        when (val result = changelogRepository.load(intent.platform)) {
                            is ResultOf.Success -> {
                                if (result.value.isEmpty()) {
                                    dispatch(Message.Error(ErrorType.EmptyData))
                                } else {
                                    dispatch(Message.Success(result.value))
                                }
                            }
                            is ResultOf.Failure -> {
                                crashlyticsTracker.recordException(result.throwable)
                                dispatch(Message.Error(ErrorType.LoadFailed))
                            }
                        }
                    }
                }
            },
            reducer = { message: Message ->
                when (message) {
                    is Message.PlatformUpdated -> copy(platform = message.platform)
                    is Message.Loading -> copy(content = State.Content.Loading)
                    is Message.Success -> copy(content = State.Content.Loaded(message.entries))
                    is Message.Error -> copy(content = State.Content.Error(message.errorType))
                }
            }
        ) {}
}
