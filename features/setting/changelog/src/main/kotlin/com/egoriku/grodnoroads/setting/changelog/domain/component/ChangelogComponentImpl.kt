package com.egoriku.grodnoroads.setting.changelog.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.setting.changelog.domain.store.ChangelogStore
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildChangelogComponent(
    componentContext: ComponentContext
): ChangelogComponent = ChangelogComponentImpl(componentContext)

internal class ChangelogComponentImpl(
    componentContext: ComponentContext
) : ChangelogComponent, KoinComponent, ComponentContext by componentContext {

    private val changelogStore: ChangelogStore = instanceKeeper.getStore(::get)

    override val state: Flow<ChangelogStore.State>
        get() = changelogStore.states
}