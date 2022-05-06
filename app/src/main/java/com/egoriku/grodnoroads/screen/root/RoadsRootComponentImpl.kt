package com.egoriku.grodnoroads.screen.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.screen.main.MainComponent
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.Child
import com.egoriku.grodnoroads.screen.root.RoadsRootComponentImpl.Configuration.Main
import com.egoriku.grodnoroads.screen.root.RoadsRootComponentImpl.Configuration.Settings
import com.egoriku.grodnoroads.screen.settings.SettingsComponent
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class RoadsRootComponentImpl(
    componentContext: ComponentContext
) : RoadsRootComponent, KoinComponent, ComponentContext by componentContext {

    private val main: (ComponentContext) -> MainComponent = {
        get { parametersOf(componentContext) }
    }

    private val settings: (ComponentContext) -> SettingsComponent = {
        get { parametersOf(componentContext) }
    }

    private val router = router<Configuration, Child>(
        initialConfiguration = Main,
        handleBackButton = true,
        key = "Root",
        childFactory = { configuration, componentContext ->
            when (configuration) {
                is Main -> Child.Main(main(componentContext))
                is Settings -> Child.Settings(settings(componentContext))
            }
        }
    )

    override val routerState: Value<RouterState<*, Child>> = router.state

    override fun openSettings() {
        router.push(Settings)
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Main : Configuration()

        @Parcelize
        object Settings : Configuration()
    }
}