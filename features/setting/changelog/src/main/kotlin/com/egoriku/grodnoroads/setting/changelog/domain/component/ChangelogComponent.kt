package com.egoriku.grodnoroads.setting.changelog.domain.component

import com.egoriku.grodnoroads.setting.changelog.domain.store.ChangelogStore
import kotlinx.coroutines.flow.Flow

interface ChangelogComponent {

    val state: Flow<ChangelogStore.State>
}