package com.egoriku.grodnoroads.settings.changelog.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.egoriku.grodnoroads.extensions.decompose.toStateFlow
import com.egoriku.grodnoroads.settings.changelog.domain.component.ChangelogFlowComponent.Child
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogEntry
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.settings.changelog.domain.store.ChangelogStore
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun buildChangelogFlowComponent(
    componentContext: ComponentContext
): ChangelogFlowComponent = ChangelogFlowComponentImpl(componentContext)

internal class ChangelogFlowComponentImpl(
    componentContext: ComponentContext
) : ChangelogFlowComponent,
    KoinComponent,
    ComponentContext by componentContext {

    private val changelogStore: ChangelogStore = instanceKeeper.getStore(::get)

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.List,
        handleBackButton = true,
        key = "ChangelogFlow",
        childFactory = ::processChild
    )

    override val childStack: StateFlow<ChildStack<*, Child>> = stack.toStateFlow()

    override fun onBack() = navigation.pop()

    override fun onAddClick() {
        navigation.pushNew(
            Config.AddEdit(
                platform = ChangelogPlatform.Android,
                entry = null
            )
        )
    }

    override fun onEditClick(entry: ChangelogEntry) {
        navigation.pushNew(
            Config.AddEdit(
                platform = entry.platform,
                entry = entry
            )
        )
    }

    private fun processChild(
        config: Config,
        componentContext: ComponentContext
    ): Child = when (config) {
        is Config.List -> Child.List(
            buildChangelogComponent(componentContext = componentContext, changelogStore = changelogStore)
        )
        is Config.AddEdit -> Child.AddEdit(
            buildChangelogAddComponent(
                componentContext = componentContext,
                platform = config.platform,
                entry = config.entry,
                changelogStore = changelogStore,
                onFinished = navigation::pop
            )
        )
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config

        @Serializable
        data class AddEdit(val platform: ChangelogPlatform, val entry: ChangelogEntry?) : Config
    }
}
