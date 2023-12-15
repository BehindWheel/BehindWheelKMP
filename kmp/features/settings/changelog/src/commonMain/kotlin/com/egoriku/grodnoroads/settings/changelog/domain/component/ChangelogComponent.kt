package com.egoriku.grodnoroads.settings.changelog.domain.component

import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore
import kotlinx.coroutines.flow.Flow

interface ChangelogComponent {

    val state: Flow<ChangelogStore.State>
}