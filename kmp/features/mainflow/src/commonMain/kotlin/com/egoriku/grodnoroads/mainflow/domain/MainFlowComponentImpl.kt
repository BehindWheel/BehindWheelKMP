package com.egoriku.grodnoroads.mainflow.domain

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.mainflow.buildTabComponent
import com.egoriku.grodnoroads.mainflow.domain.MainFlowComponent.Child
import com.egoriku.grodnoroads.settings.changelog.domain.component.buildChangelogComponent
import com.egoriku.grodnoroads.settings.faq.domain.component.buildFaqComponent
import com.egoriku.grodnoroads.shared.models.Page
import kotlinx.serialization.Serializable

fun buildMainFlowComponent(
    componentContext: ComponentContext
): MainFlowComponent = MainFlowComponentImpl(componentContext)

internal class MainFlowComponentImpl(
    componentContext: ComponentContext
) : MainFlowComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Tabs,
        handleBackButton = true,
        key = "MainFlowStack",
        childFactory = ::processChild
    )

    override val childStack: Value<ChildStack<*, Child>> = stack

    override fun onBack() = navigation.pop()

    private fun processChild(
        config: Config,
        componentContext: ComponentContext
    ) = when (config) {
        is Config.Tabs -> Child.Tabs(
            buildTabComponent(componentContext = componentContext, onOpenPage = ::open)
        )
        is Config.Appearance -> Child.Appearance
        Config.Alerts -> TODO()
        Config.Changelog -> Child.Changelog(buildChangelogComponent(componentContext))
        Config.FAQ -> Child.Faq(buildFaqComponent(componentContext))
        Config.MapSettings -> TODO()
        Config.NextFeatures -> TODO()
    }

    @OptIn(ExperimentalDecomposeApi::class)
    private fun open(page: Page) {
        when (page) {
            Page.Appearance -> navigation.pushNew(Config.Appearance)
            Page.Map -> navigation.pushNew(Config.MapSettings)
            Page.Alerts -> navigation.pushNew(Config.Alerts)
            Page.Changelog -> navigation.pushNew(Config.Changelog)
            Page.NextFeatures -> navigation.pushNew(Config.NextFeatures)
            Page.FAQ -> navigation.pushNew(Config.FAQ)
        }
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Tabs : Config()

        @Serializable
        data object Appearance : Config()

        @Serializable
        data object MapSettings : Config()

        @Serializable
        data object Alerts : Config()

        @Serializable
        data object Changelog : Config()

        @Serializable
        data object NextFeatures : Config()

        @Serializable
        data object FAQ : Config()
    }
}