package com.egoriku.grodnoroads.screen.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.eventreporting.domain.model.ReportParams
import com.egoriku.grodnoroads.map.domain.component.buildMapComponent
import com.egoriku.grodnoroads.screen.main.MainComponent.Child
import com.egoriku.grodnoroads.setting.domain.component.buildSettingsComponent
import com.egoriku.grodnoroads.shared.appcomponent.Page
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent

fun buildMainComponent(
    componentContext: ComponentContext,
    onOpenReporting: () -> Unit,
    onOpen: (Page) -> Unit
): MainComponent = MainComponentImpl(
    componentContext = componentContext,
    onOpenReporting = onOpenReporting,
    onOpen = onOpen
)

internal class MainComponentImpl(
    componentContext: ComponentContext,
    private val onOpenReporting: () -> Unit,
    private val onOpen: (Page) -> Unit
) : MainComponent, ComponentContext by componentContext, KoinComponent {

    private val navigation = StackNavigation<Config>()

    private val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
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

    override fun processReporting(params: ReportParams) {
        (stack.active.instance as? Child.Map)?.component?.processReporting(params)
    }

    private fun child(
        configuration: Config,
        componentContext: ComponentContext,
    ) = when (configuration) {
        is Config.Map -> Child.Map(
            component = buildMapComponent(
                componentContext = componentContext,
                onOpenReporting = onOpenReporting
            )
        )
        is Config.Settings -> Child.Settings(
            component = buildSettingsComponent(
                componentContext = componentContext,
                onOpen = onOpen
            )
        )
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Map : Config()

        @Serializable
        data object Settings : Config()
    }
}