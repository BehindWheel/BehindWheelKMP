package com.egoriku.grodnoroads.screen.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.screen.main.MainComponent
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.Child
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.ThemeState
import com.egoriku.grodnoroads.screen.root.RoadsRootComponentImpl.Configuration.Main
import com.egoriku.grodnoroads.screen.settings.store.SettingsStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class RoadsRootComponentImpl(
    componentContext: ComponentContext
) : RoadsRootComponent, KoinComponent, ComponentContext by componentContext {

    private val settingsStore = instanceKeeper.getStore { get<SettingsStore>() }
    private val settings = settingsStore.states.map { it.settingsState }

    private val main: (ComponentContext) -> MainComponent = {
        get { parametersOf(componentContext) }
    }

    private val router = router<Configuration, Child>(
        initialConfiguration = Main,
        handleBackButton = true,
        key = "Root",
        childFactory = { configuration, componentContext ->
            when (configuration) {
                is Main -> Child.Main(main(componentContext))
            }
        }
    )

    override val routerState: Value<RouterState<*, Child>> = router.state

    override val themeState: Flow<ThemeState> = settings.map {
        ThemeState(theme = it.appTheme.current)
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Main : Configuration()
    }
}