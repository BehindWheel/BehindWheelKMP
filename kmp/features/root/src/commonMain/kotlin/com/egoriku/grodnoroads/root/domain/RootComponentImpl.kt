package com.egoriku.grodnoroads.root.domain

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.mainflow.domain.buildMainFlowComponent
import com.egoriku.grodnoroads.onboarding.domain.buildOnboardingComponent
import com.egoriku.grodnoroads.root.domain.RootComponent.Child
import kotlinx.serialization.Serializable

class RootComponentImpl(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.MainFlow,
        handleBackButton = true,
        key = "RootStack",
        childFactory = ::processChild
    )

    override val childStack: Value<ChildStack<*, Child>> = stack

    private fun processChild(
        config: Config,
        componentContext: ComponentContext
    ) = when (config) {
        is Config.Onboarding -> Child.Onboarding(
            buildOnboardingComponent(
                componentContext = componentContext,
                onFinishOnboarding = {
                    navigation.replaceAll(Config.MainFlow)
                }
            )
        )
        is Config.MainFlow -> Child.MainFlow(
            buildMainFlowComponent(componentContext = componentContext)
        )
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object MainFlow : Config()

        @Serializable
        data object Onboarding : Config()
    }
}