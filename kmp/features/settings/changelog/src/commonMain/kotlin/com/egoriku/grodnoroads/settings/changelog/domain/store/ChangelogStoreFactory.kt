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
        Store<Intent, State, ChangelogStore.Label> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                var loadJob by smartJob()

                onIntent<Intent.SelectPlatform> { intent ->
                    val current = state()
                    if (current.platform == intent.platform && current.content is State.Content.Loaded) {
                        return@onIntent
                    }

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

                onIntent<Intent.DeleteEntry> { intent ->
                    val platform = state().platform ?: return@onIntent
                    loadJob = launch {
                        when (val deleteResult = changelogRepository.delete(intent.id)) {
                            is ResultOf.Success -> {
                                dispatch(Message.Loading)

                                when (val loadResult = changelogRepository.load(platform)) {
                                    is ResultOf.Success -> {
                                        if (loadResult.value.isEmpty()) {
                                            dispatch(Message.Error(ErrorType.EmptyData))
                                        } else {
                                            dispatch(Message.Success(loadResult.value))
                                        }
                                    }
                                    is ResultOf.Failure -> {
                                        crashlyticsTracker.recordException(loadResult.throwable)
                                        dispatch(Message.Error(ErrorType.LoadFailed))
                                    }
                                }
                            }
                            is ResultOf.Failure -> {
                                crashlyticsTracker.recordException(deleteResult.throwable)
                                publish(ChangelogStore.Label.DeleteFailed)
                            }
                        }
                    }
                }
                onIntent<Intent.EntryUpserted> { intent ->
                    dispatch(Message.EntryUpserted(intent.entry))
                }
            },
            reducer = { message: Message ->
                when (message) {
                    is Message.PlatformUpdated -> copy(platform = message.platform)
                    is Message.Loading -> copy(content = State.Content.Loading)
                    is Message.Success -> copy(content = State.Content.Loaded(message.entries))
                    is Message.Error -> copy(content = State.Content.Error(message.errorType))
                    is Message.EntryUpserted -> {
                        val currentContent = content

                        if (message.entry.platform != platform) {
                            this
                        } else {
                            val existingEntries = when (currentContent) {
                                is State.Content.Loaded -> currentContent.entries
                                else -> emptyList()
                            }
                            val updatedEntries = existingEntries
                                .filterNot { it.id == message.entry.id }
                                .plus(message.entry)
                                .sortedByDescending { it.releaseDateMillis }

                            copy(content = State.Content.Loaded(updatedEntries))
                        }
                    }
                }
            }
        ) {}
}
