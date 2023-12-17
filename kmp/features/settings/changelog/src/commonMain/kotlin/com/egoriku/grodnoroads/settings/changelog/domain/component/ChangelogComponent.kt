package com.egoriku.grodnoroads.settings.changelog.domain.component

import com.egoriku.grodnoroads.extensions.common.CStateFlow
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("ChangelogComponent", exact = true)
interface ChangelogComponent {
    val state: CStateFlow<ChangelogStore.State>
}