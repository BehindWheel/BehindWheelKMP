package com.egoriku.grodnoroads.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.egoriku.grodnoroads.root.RootComponent.Child

class RootComponentImpl(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        initialConfiguration = Config.MainFlow,
        handleBackButton = true,
        key = "RootStack",
        childFactory = ::processChild
    )

    override val childStack: Value<ChildStack<*, Child>> = stack

    private fun processChild(config: Config, componentContext: ComponentContext) = when (config) {
        is Config.Onboarding -> Child.Onboarding
        is Config.MainFlow -> Child.MainFlow
    }

    private sealed class Config : Parcelable {
        @Parcelize
        data object MainFlow : Config()

        @Parcelize
        data object Onboarding : Config()
    }
}