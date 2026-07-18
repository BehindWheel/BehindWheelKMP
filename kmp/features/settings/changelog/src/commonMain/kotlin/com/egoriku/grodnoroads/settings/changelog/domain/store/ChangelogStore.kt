package com.egoriku.grodnoroads.settings.changelog.domain.store

import androidx.compose.runtime.Stable
import com.arkivanov.mvikotlin.core.store.Store
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogEntry
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.model.ErrorType
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.State

interface ChangelogStore : Store<ChangelogStore.Intent, State, Nothing> {

    sealed interface Intent {
        data class SelectPlatform(val platform: ChangelogPlatform) : Intent
    }

    sealed interface Message {
        data class PlatformUpdated(val platform: ChangelogPlatform) : Message
        data object Loading : Message
        data class Success(val entries: List<ChangelogEntry>) : Message
        data class Error(val errorType: ErrorType) : Message
    }

    data class State(
        val platform: ChangelogPlatform? = null,
        val content: Content = Content.Loading
    ) {
        @Stable
        sealed interface Content {
            data object Loading : Content
            data class Loaded(val entries: List<ChangelogEntry>) : Content
            data class Error(val errorType: ErrorType) : Content
        }
    }
}
