package com.egoriku.grodnoroads.settings.changelog.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.State.Content
import kotlinx.coroutines.flow.StateFlow

@Stable
interface ChangelogComponent {
    val content: StateFlow<Content>
    val platform: StateFlow<ChangelogPlatform?>

    fun selectPlatform(platform: ChangelogPlatform)
}
