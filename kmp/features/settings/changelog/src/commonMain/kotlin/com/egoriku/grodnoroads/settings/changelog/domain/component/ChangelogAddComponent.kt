package com.egoriku.grodnoroads.settings.changelog.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import kotlinx.coroutines.flow.StateFlow

@Stable
interface ChangelogAddComponent {

    val state: StateFlow<State>

    fun setPlatform(platform: ChangelogPlatform)
    fun setVersionName(versionName: String)
    fun setNotes(notes: String)
    fun setReleaseDateMillis(millis: Long)
    fun save()
    fun onBack()

    data class State(
        val isEditing: Boolean,
        val platform: ChangelogPlatform,
        val versionName: String,
        val notes: String,
        val releaseDateMillis: Long,
        val isSaving: Boolean,
        val saveError: Throwable?
    )
}
