package com.egoriku.grodnoroads.intro.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.egoriku.grodnoroads.cityselector.domain.component.buildCitySelectorComponent
import com.egoriku.grodnoroads.extensions.decompose.toStateFlow
import com.egoriku.grodnoroads.intro.domain.component.IntroComponent.Child
import com.egoriku.grodnoroads.onboarding.domain.component.buildOnboardingComponent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

fun buildIntroComponent(
    componentContext: ComponentContext,
    onFinishIntro: () -> Unit
): IntroComponent = IntroComponentImpl(
    componentContext = componentContext,
    onFinishIntro = onFinishIntro
)

internal class IntroComponentImpl(
    componentContext: ComponentContext,
    private val onFinishIntro: () -> Unit
) : IntroComponent,
    ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Onboarding,
        handleBackButton = true,
        key = "Intro",
        childFactory = ::processChild
    )

    override val childStack: StateFlow<ChildStack<*, Child>> = stack.toStateFlow()

    private fun processChild(
        config: Config,
        componentContext: ComponentContext
    ) = when (config) {
        is Config.Onboarding -> {
            Child.Onboarding(
                component = buildOnboardingComponent(
                    componentContext = componentContext,
                    onCompleteOnboarding = {
                        navigation.replaceCurrent(Config.CitySelector)
                    }
                )
            )
        }
        is Config.CitySelector -> {
            Child.CitySelector(
                component = buildCitySelectorComponent(
                    componentContext = componentContext,
                    onFinishIntro = onFinishIntro
                )
            )
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Onboarding : Config

        @Serializable
        data object CitySelector : Config
    }
}
