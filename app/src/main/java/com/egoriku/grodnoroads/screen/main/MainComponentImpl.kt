package com.egoriku.grodnoroads.screen.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.egoriku.grodnoroads.screen.main.MainComponent.Child
import com.egoriku.grodnoroads.screen.map.MapComponent
import com.egoriku.grodnoroads.screen.settings.SettingsComponent
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class MainComponentImpl(
    componentContext: ComponentContext
) : MainComponent, ComponentContext by componentContext, KoinComponent {

    private val map: (ComponentContext) -> MapComponent = {
        get { parametersOf(componentContext) }
    }

    private val settings: (ComponentContext) -> SettingsComponent = {
        get { parametersOf(it) }
    }

    private val navigation = StackNavigation<Config>()

    private val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Settings,
        handleBackButton = true,
        key = "Main",
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, Child>> = stack

    override fun onSelectTab(index: Int) {
        navigation.replaceCurrent(
            when (index) {
                0 -> Config.Map
                1 -> Config.Settings
                else -> throw IllegalArgumentException("index $index is not defined in app")
            }
        )
    }

    private fun child(
        configuration: Config,
        componentContext: ComponentContext,
    ) = when (configuration) {
        is Config.Map -> Child.Map(map(componentContext))
        is Config.Settings -> Child.Settings(settings(componentContext))
    }

    sealed class Config : Parcelable {
        @Parcelize
        object Map : Config()

        @Parcelize
        object Settings : Config()
    }
}