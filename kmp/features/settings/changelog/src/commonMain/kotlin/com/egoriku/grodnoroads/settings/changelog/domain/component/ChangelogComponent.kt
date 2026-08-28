package com.egoriku.grodnoroads.settings.changelog.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.State.Content
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Stable
interface ChangelogComponent {
    val content: StateFlow<Content>
    val platform: StateFlow<ChangelogPlatform?>
    val allowedModify: StateFlow<Boolean>
    val labels: Flow<Label>

    fun selectPlatform(platform: ChangelogPlatform)
    fun deleteEntry(id: String)

    sealed interface Label {
        data object DeleteFailed : Label
    }
}
