package com.egoriku.grodnoroads.settings.changelog.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.auth.Auth
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.Intent
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore.State.Content
import com.egoriku.grodnoroads.shared.components.AppBuildConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildChangelogComponent(
    componentContext: ComponentContext,
    changelogStore: ChangelogStore
): ChangelogComponent = ChangelogComponentImpl(componentContext, changelogStore)

internal class ChangelogComponentImpl(
    componentContext: ComponentContext,
    private val changelogStore: ChangelogStore
) : ChangelogComponent,
    KoinComponent,
    ComponentContext by componentContext {

    private val componentScope = coroutineScope()
    private val auth: Auth = get()

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

    override val allowedModify: StateFlow<Boolean> = auth.isSignedIn
        .map { signedIn -> AppBuildConfig.isDebug && signedIn }
        .stateIn(
            scope = componentScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    override val labels: Flow<ChangelogComponent.Label> = changelogStore.labels
        .map { ChangelogComponent.Label.DeleteFailed }

    override fun selectPlatform(platform: ChangelogPlatform) {
        changelogStore.accept(Intent.SelectPlatform(platform))
    }

    override fun deleteEntry(id: String) {
        changelogStore.accept(Intent.DeleteEntry(id))
    }
}
