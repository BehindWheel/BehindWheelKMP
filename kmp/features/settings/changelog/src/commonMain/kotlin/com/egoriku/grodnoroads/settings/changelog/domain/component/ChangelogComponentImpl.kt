package com.egoriku.grodnoroads.settings.changelog.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.Intent
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.State.Content
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildChangelogComponent(
    componentContext: ComponentContext
): ChangelogComponent = ChangelogComponentImpl(componentContext)

internal class ChangelogComponentImpl(
    componentContext: ComponentContext
) : ChangelogComponent,
    KoinComponent,
    ComponentContext by componentContext {

    private val componentScope = coroutineScope()
    private val changelogStore: ChangelogStore = instanceKeeper.getStore(::get)

    override val content: StateFlow<Content> = changelogStore.states
        .map { it.content }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Content.Loading
        )

    override val platform: StateFlow<ChangelogPlatform?> = changelogStore.states
        .map { it.platform }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    override fun selectPlatform(platform: ChangelogPlatform) {
        changelogStore.accept(Intent.SelectPlatform(platform))
    }
}
