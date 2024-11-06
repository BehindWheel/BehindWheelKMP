package com.egoriku.grodnoroads.root.domain

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.egoriku.grodnoroads.extensions.decompose.coroutineScope
import com.egoriku.grodnoroads.extensions.decompose.toStateFlow
import com.egoriku.grodnoroads.intro.domain.component.buildIntroComponent
import com.egoriku.grodnoroads.mainflow.domain.buildMainFlowComponent
import com.egoriku.grodnoroads.root.domain.RootComponent.Child
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme
import com.egoriku.grodnoroads.shared.persistent.appearance.appTheme
import com.egoriku.grodnoroads.shared.persistent.intro.showIntro
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class RootComponentImpl(
    componentContext: ComponentContext
) : RootComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val dataStore by inject<DataStore<Preferences>>()
    private val coroutineScope = coroutineScope()

    private val navigation = StackNavigation<Config>()
    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.MainFlow,
        handleBackButton = true,
        key = "Root",
        childFactory = ::processChild
    )

    init {
        runBlocking {
            if (dataStore.data.first().showIntro) {
                navigation.replaceAll(Config.Intro)
            }
        }
    }

    override val childStack: StateFlow<ChildStack<*, Child>> = stack.toStateFlow()

    override val theme: StateFlow<Theme?>
        get() = dataStore.data
            .map { Theme.fromOrdinal(it.appTheme.theme) }
            .distinctUntilChanged()
            .stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, initialValue = null)

    private fun processChild(
        config: Config,
        componentContext: ComponentContext
    ) = when (config) {
        is Config.Intro -> Child.Intro(
            buildIntroComponent(
                componentContext = componentContext,
                onFinishIntro = {
                    navigation.replaceAll(Config.MainFlow)
                }
            )
        )
        is Config.MainFlow -> Child.MainFlow(
            buildMainFlowComponent(componentContext = componentContext)
        )
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object MainFlow : Config

        @Serializable
        data object Intro : Config
    }
}
