package com.egoriku.grodnoroads.screen.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.replaceCurrent
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
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

    private val router = router<Config, Child>(
        initialConfiguration = Config.Map,
        key = "Main"
    ) { configuration, componentContext ->
        when (configuration) {
            is Config.Map -> Child.Map(map(componentContext))
            is Config.Settings -> Child.Settings(settings(componentContext))
        }
    }

    override val routerState: Value<RouterState<*, Child>> = router.state

    override val activeChildIndex: Value<Int> = routerState.map {
        it.activeChild.instance.index
    }

    override fun onSelectTab(index: Int) {
        router.replaceCurrent(
            when (index) {
                0 -> Config.Map
                1 -> Config.Settings
                else -> throw IllegalArgumentException("index $index is not defined in app")
            }
        )
    }

    sealed class Config : Parcelable {
        @Parcelize
        object Map : Config()

        @Parcelize
        object Settings : Config()
    }
}