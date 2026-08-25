package com.egoriku.grodnoroads.root.domain

import androidx.compose.runtime.Stable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.extensions.decompose.toStateFlow
import com.egoriku.grodnoroads.intro.domain.component.IntroComponent
import com.egoriku.grodnoroads.intro.domain.component.buildIntroComponent
import com.egoriku.grodnoroads.mainflow.domain.MainFlowComponent
import com.egoriku.grodnoroads.mainflow.domain.buildMainFlowComponent
import com.egoriku.grodnoroads.root.domain.RootComponent.Child
import com.egoriku.grodnoroads.root.domain.RootStore.Intent.UpdateThemeIntent
import com.egoriku.grodnoroads.shared.persistent.intro.showIntro
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject

fun buildRootComponent(
    componentContext: ComponentContext
): RootComponent = RootComponentImpl(componentContext)

@Stable
interface RootComponent {
    val appTheme: Flow<AppTheme?>
    val childStack: StateFlow<ChildStack<*, Child>>

    sealed interface Child {
        data class Intro(val component: IntroComponent) : Child
        data class MainFlow(val component: MainFlowComponent) : Child
    }
}

internal class RootComponentImpl(
    componentContext: ComponentContext
) : RootComponent,
    ComponentContext by componentContext,
    KoinComponent {

    private val rootStore = instanceKeeper.getStore<RootStore>(::get)

    private val dataStore by inject<DataStore<Preferences>>()

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
        bind(lifecycle, BinderLifecycleMode.CREATE_DESTROY) {
            rootStore.labels bindTo ::bindLabel
        }

        runBlocking {
            if (dataStore.data.first().showIntro) {
                navigation.replaceAll(Config.Intro)
            }
        }
    }

    override val childStack: StateFlow<ChildStack<*, Child>> = stack.toStateFlow()
    override val appTheme = rootStore.states.map { it.appTheme }

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

    private fun bindLabel(label: RootStore.Label) {
        when (label) {
            is RootStore.Label.UpdateThemeLabel -> rootStore.accept(UpdateThemeIntent)
        }
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object MainFlow : Config

        @Serializable
        data object Intro : Config
    }
}
