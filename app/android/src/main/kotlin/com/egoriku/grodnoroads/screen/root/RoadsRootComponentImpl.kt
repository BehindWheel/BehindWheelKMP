package com.egoriku.grodnoroads.screen.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.extensions.common.StateData
import com.egoriku.grodnoroads.mainflow.buildTabComponent
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.Child
import com.egoriku.grodnoroads.screen.root.store.RootStore
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory.Intent
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.setting.map.domain.component.buildMapSettingsComponent
import com.egoriku.grodnoroads.shared.models.Page
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class RoadsRootComponentImpl(
    componentContext: ComponentContext
) : RoadsRootComponent, KoinComponent, ComponentContext by componentContext {

    private val rootStore: RootStore = instanceKeeper.getStore(::get)

    private val navigation = StackNavigation<Config>()

    private val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Main,
        handleBackButton = true,
        key = "Root",
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, Child>> = stack

    override val themeState: Flow<StateData<Theme>> =
        rootStore.states.map {
            when (it.theme) {
                null -> StateData.Idle
                else -> StateData.Loaded(it.theme)
            }
        }

    override val headlampDialogState: Flow<HeadLampType> = rootStore.states.map { it.headLampType }

    override fun closeHeadlampDialog() {
        rootStore.accept(Intent.CloseDialog)
    }

    @OptIn(ExperimentalDecomposeApi::class)
    override fun open(page: Page) {
        when (page) {
            Page.Map -> navigation.pushNew(Config.MapSettings)
            else -> {}
        }
    }

    override fun onBack() = navigation.pop()

    private fun child(config: Config, componentContext: ComponentContext) = when (config) {
        is Config.Main -> Child.Main(
            component = buildTabComponent(
                componentContext = componentContext,
                onOpenPage = ::open
            )
        )

        is Config.MapSettings -> Child.Map(
            mapSettingsComponent = buildMapSettingsComponent(componentContext)
        )
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Main : Config()

        @Serializable
        data object MapSettings : Config()
    }
}