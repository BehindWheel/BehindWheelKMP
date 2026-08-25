package com.egoriku.grodnoroads.settings.debugtools.domain

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.egoriku.grodnoroads.extensions.decompose.toStateFlow
import com.egoriku.grodnoroads.settings.debugtools.domain.DebugToolsComponent.Child
import com.egoriku.grodnoroads.settings.debugtools.domain.auth.buildAuthComponent
import com.egoriku.grodnoroads.settings.debugtools.domain.datastore.buildDataStoreEditComponent
import com.egoriku.grodnoroads.settings.debugtools.domain.root.buildDebugToolsRootComponent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

fun buildDebugToolsComponent(
    componentContext: ComponentContext
): DebugToolsComponent = DebugToolsComponentImpl(componentContext)

internal class DebugToolsComponentImpl(
    componentContext: ComponentContext
) : DebugToolsComponent,
    ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Root,
        handleBackButton = true,
        key = "DebugTools",
        childFactory = ::processChild
    )

    override val childStack: StateFlow<ChildStack<*, Child>> = stack.toStateFlow()

    override fun onBack() = navigation.pop()

    private fun processChild(
        config: Config,
        componentContext: ComponentContext
    ) = when (config) {
        Config.Root -> Child.Root(
            buildDebugToolsRootComponent(
                componentContext = componentContext,
                onOpenUiKit = { navigation.pushNew(Config.UiKit) },
                onOpenDataStoreEdit = { navigation.pushNew(Config.DataStoreEdit) },
                onOpenPalette = { navigation.pushNew(Config.Palette) },
                onOpenAuth = { navigation.pushNew(Config.Auth) },
                onOpenSpecialEvents = { navigation.pushNew(Config.SpecialEvents) }
            )
        )
        Config.UiKit -> Child.UIKit
        Config.DataStoreEdit -> Child.DataStoreEdit(buildDataStoreEditComponent(componentContext))
        Config.Palette -> Child.Palette
        Config.Auth -> Child.Auth(buildAuthComponent(componentContext))
        Config.SpecialEvents -> Child.SpecialEvents
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Root : Config

        @Serializable
        data object UiKit : Config

        @Serializable
        data object DataStoreEdit : Config

        @Serializable
        data object Palette : Config

        @Serializable
        data object Auth : Config

        @Serializable
        data object SpecialEvents : Config
    }
}
