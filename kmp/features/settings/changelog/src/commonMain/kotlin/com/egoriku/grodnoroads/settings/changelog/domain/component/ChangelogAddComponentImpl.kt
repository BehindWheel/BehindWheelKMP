package com.egoriku.grodnoroads.settings.changelog.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.egoriku.grodnoroads.extensions.DateTime
import com.egoriku.grodnoroads.extensions.common.ResultOf
import com.egoriku.grodnoroads.settings.changelog.domain.component.ChangelogAddComponent.State
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogEntry
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.model.NewChangelogEntry
import com.egoriku.grodnoroads.settings.changelog.domain.repository.ChangelogRepository
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

private val NEW_ENTRY_NOTES_TEMPLATE = """
    Добавлено:
    🔸

    Изменено:
    🔸

    Баги:
    🐞
""".trimIndent()

fun buildChangelogAddComponent(
    componentContext: ComponentContext,
    changelogStore: ChangelogStore,
    onFinished: () -> Unit,
    entry: ChangelogEntry? = null,
    platform: ChangelogPlatform = ChangelogPlatform.Android
): ChangelogAddComponent = ChangelogAddComponentImpl(
    componentContext = componentContext,
    platform = platform,
    entry = entry,
    changelogStore = changelogStore,
    onFinished = onFinished
)

internal class ChangelogAddComponentImpl(
    componentContext: ComponentContext,
    platform: ChangelogPlatform,
    private val entry: ChangelogEntry?,
    private val changelogStore: ChangelogStore,
    private val onFinished: () -> Unit
) : ChangelogAddComponent,
    KoinComponent,
    ComponentContext by componentContext {

    private val componentScope = coroutineScope()
    private val changelogRepository: ChangelogRepository = get()

    private val _state = MutableStateFlow(
        State(
            isEditing = entry != null,
            platform = platform,
            versionName = entry?.versionName.orEmpty(),
            notes = entry?.notes ?: NEW_ENTRY_NOTES_TEMPLATE,
            releaseDateMillis = entry?.releaseDateMillis ?: DateTime.currentTimeMillis(),
            isSaving = false,
            saveError = null
        )
    )
    override val state: StateFlow<State> = _state.asStateFlow()

    override fun setPlatform(platform: ChangelogPlatform) {
        _state.update { it.copy(platform = platform) }
    }

    override fun setVersionName(versionName: String) {
        _state.update { it.copy(versionName = versionName) }
    }

    override fun setNotes(notes: String) {
        _state.update { it.copy(notes = notes) }
    }

    override fun setReleaseDateMillis(millis: Long) {
        _state.update { it.copy(releaseDateMillis = millis) }
    }

    override fun save() {
        val current = _state.value
        componentScope.launch {
            _state.update { it.copy(isSaving = true, saveError = null) }

            val newEntry = NewChangelogEntry(
                versionName = current.versionName,
                notes = current.notes,
                releaseDateMillis = current.releaseDateMillis
            )

            val result = when (val id = entry?.id) {
                null -> changelogRepository.add(newEntry, current.platform)
                else -> changelogRepository.update(id, newEntry, current.platform)
            }

            when (result) {
                is ResultOf.Success -> {
                    changelogStore.accept(ChangelogStore.Intent.EntryUpserted(result.value))
                    onFinished()
                }
                is ResultOf.Failure -> _state.update {
                    it.copy(isSaving = false, saveError = result.throwable)
                }
            }
        }
    }

    override fun onBack() = onFinished()
}
