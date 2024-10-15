package com.egoriku.grodnoroads.mainflow.domain

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.egoriku.grodnoroads.extensions.decompose.toStateFlow
import com.egoriku.grodnoroads.mainflow.domain.MainFlowComponent.Child
import com.egoriku.grodnoroads.settings.alerts.domain.component.buildAlertsComponent
import com.egoriku.grodnoroads.settings.appearance.domain.component.buildAppearanceComponent
import com.egoriku.grodnoroads.settings.changelog.domain.component.buildChangelogComponent
import com.egoriku.grodnoroads.settings.faq.domain.component.buildFaqComponent
import com.egoriku.grodnoroads.settings.map.domain.component.buildMapSettingsComponent
import com.egoriku.grodnoroads.shared.models.Page
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

fun buildMainFlowComponent(
    componentContext: ComponentContext
): MainFlowComponent = MainFlowComponentImpl(componentContext)

internal class MainFlowComponentImpl(
    componentContext: ComponentContext
) : MainFlowComponent,
    ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Tabs,
        handleBackButton = true,
        key = "MainFlowStack",
        childFactory = ::processChild
    )

    override val childStack: StateFlow<ChildStack<*, Child>> = stack.toStateFlow()

    override fun onBack() = navigation.pop()

    private fun processChild(
        config: Config,
        componentContext: ComponentContext
    ) = when (config) {
        is Config.Tabs -> Child.Tabs(
            buildTabComponent(
                componentContext = componentContext,
                onOpenPage = ::open
            )
        )
        is Config.Appearance -> Child.Appearance(buildAppearanceComponent(componentContext))
        is Config.Alerts -> Child.Alerts(buildAlertsComponent(componentContext))
        is Config.Changelog -> Child.Changelog(buildChangelogComponent(componentContext))
        is Config.FAQ -> Child.FAQ(buildFaqComponent(componentContext))
        is Config.MapSettings -> Child.MapSettings(buildMapSettingsComponent(componentContext))
    }

    @OptIn(ExperimentalDecomposeApi::class)
    private fun open(page: Page) {
        when (page) {
            Page.Alerts -> navigation.pushNew(Config.Alerts)
            Page.Appearance -> navigation.pushNew(Config.Appearance)
            Page.Changelog -> navigation.pushNew(Config.Changelog)
            Page.FAQ -> navigation.pushNew(Config.FAQ)
            Page.MapSettings -> navigation.pushNew(Config.MapSettings)
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Tabs : Config

        @Serializable
        data object Appearance : Config

        @Serializable
        data object MapSettings : Config

        @Serializable
        data object Alerts : Config

        @Serializable
        data object Changelog : Config

        @Serializable
        data object FAQ : Config
    }
}
