package com.egoriku.grodnoroads.screen.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.egoriku.grodnoroads.map.domain.component.buildMapComponent
import com.egoriku.grodnoroads.screen.main.MainComponent.Child
import com.egoriku.grodnoroads.setting.domain.component.buildSettingsComponent
import com.egoriku.grodnoroads.shared.appcomponent.Page
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent

fun buildMainComponent(
    componentContext: ComponentContext,
    onOpen: (Page) -> Unit
): MainComponent = MainComponentImpl(
    componentContext = componentContext,
    onOpen = onOpen
)

internal class MainComponentImpl(
    componentContext: ComponentContext,
    private val onOpen: (Page) -> Unit
) : MainComponent, ComponentContext by componentContext, KoinComponent {

    private val navigation = StackNavigation<Config>()

    private val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Map,
        handleBackButton = true,
        key = "Main",
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, Child>> = stack

    override fun onSelectTab(index: Int) {
        if (index == 0) {
            navigation.replaceAll(Config.Map)
        } else {
            navigation.bringToFront(Config.Settings)
        }
    }

    private fun child(
        configuration: Config,
        componentContext: ComponentContext,
    ) = when (configuration) {
        is Config.Map -> Child.Map(component = buildMapComponent(componentContext))
        is Config.Settings -> Child.Settings(
            component = buildSettingsComponent(
                componentContext = componentContext,
                onOpen = onOpen
            )
        )
    }

    private sealed class Config : Parcelable {
        @Parcelize
        object Map : Config()

        @Parcelize
        object Settings : Config()
    }
}