package com.egoriku.grodnoroads.settings.changelog.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore
import kotlinx.coroutines.flow.StateFlow

@Stable
interface ChangelogComponent {
    val state: StateFlow<ChangelogStore.State>
}
