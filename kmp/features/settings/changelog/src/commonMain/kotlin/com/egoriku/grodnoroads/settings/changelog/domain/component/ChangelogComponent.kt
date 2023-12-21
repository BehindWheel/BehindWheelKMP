package com.egoriku.grodnoroads.settings.changelog.domain.component

import com.egoriku.grodnoroads.extensions.CStateFlow
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore

interface ChangelogComponent {
    val state: CStateFlow<ChangelogStore.State>
}