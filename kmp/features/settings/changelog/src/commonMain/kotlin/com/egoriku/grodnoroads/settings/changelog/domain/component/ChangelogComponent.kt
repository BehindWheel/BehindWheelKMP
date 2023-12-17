package com.egoriku.grodnoroads.settings.changelog.domain.component

import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore
import kotlinx.coroutines.flow.Flow
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("ChangelogComponent", exact = true)
interface ChangelogComponent {

    val state: Flow<ChangelogStore.State>
}