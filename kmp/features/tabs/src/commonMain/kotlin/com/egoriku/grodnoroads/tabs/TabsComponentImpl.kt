package com.egoriku.grodnoroads.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.appsettings.domain.buildAppSettingsComponent
import com.egoriku.grodnoroads.shared.models.Page
import com.egoriku.grodnoroads.tabs.TabsComponent.Child
import kotlinx.serialization.Serializable

fun buildTabComponent(
    componentContext: ComponentContext,
    onOpenPage: (Page) -> Unit
): TabsComponent = TabsComponentImpl(
    componentContext = componentContext,
    onOpenPage = onOpenPage
)

internal class TabsComponentImpl(
    componentContext: ComponentContext,
    private val onOpenPage: (Page) -> Unit
) : TabsComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Map,
        handleBackButton = true,
        key = "TabStack",
        childFactory = ::processChild
    )

    override val childStack: Value<ChildStack<*, Child>> = stack

    private fun processChild(
        config: Config,
        componentContext: ComponentContext
    ) = when (config) {
        is Config.Map -> Child.Map
        is Config.AppSettings -> Child.AppSettings(
            buildAppSettingsComponent(
                componentContext = componentContext,
                onOpen = onOpenPage
            )
        )
    }

    /*
     is Config.Map -> Child.Map(component = buildMapComponent(componentContext))
        is Config.Settings -> Child.Settings(
            component = buildSettingsComponent(
                componentContext = componentContext,
                onOpen = onOpen
            )
        )
     */

    override fun onSelectTab(index: Int) {
        if (index == 0) {
            navigation.replaceAll(Config.Map)
        } else {
            navigation.bringToFront(Config.AppSettings)
        }
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Map : Config()

        @Serializable
        data object AppSettings : Config()
    }
}