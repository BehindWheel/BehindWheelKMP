package com.egoriku.grodnoroads.settings.changelog.domain.component

import com.egoriku.grodnoroads.extensions.LoremIpsum
import com.egoriku.grodnoroads.extensions.coroutines.stateFlowOf
import com.egoriku.grodnoroads.settings.changelog.domain.model.ReleaseNotes
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.StateFlow

class ChangelogComponentPreview : ChangelogComponent {

    override val state: StateFlow<ChangelogStore.State>
        get() = stateFlowOf {
            ChangelogStore.State(
                isLoading = false,
                releaseNotes = persistentListOf(
                    ReleaseNotes(
                        versionCode = 102,
                        versionName = "1.0.2",
                        notes = LoremIpsum.generateLoremIpsum(50),
                        releaseDate = "3 October, 2023"
                    ),
                    ReleaseNotes(
                        versionCode = 101,
                        versionName = "1.0.1",
                        notes = LoremIpsum.generateLoremIpsum(30),
                        releaseDate = "5 June, 2023"
                    ),
                    ReleaseNotes(
                        versionCode = 100,
                        versionName = "1.0.0",
                        notes = LoremIpsum.generateLoremIpsum(100),
                        releaseDate = "24 May, 2023"
                    )
                )
            )
        }
}
