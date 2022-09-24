package com.egoriku.grodnoroads.screen.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.screen.main.MainComponent
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.Child
import com.egoriku.grodnoroads.screen.root.RoadsRootComponentImpl.Configuration.Main
import com.egoriku.grodnoroads.screen.root.store.RootStore
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory.Intent
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class RoadsRootComponentImpl(
    componentContext: ComponentContext
) : RoadsRootComponent, KoinComponent, ComponentContext by componentContext {

    private val rootStore: RootStore = instanceKeeper.getStore(::get)

    private val main: (ComponentContext) -> MainComponent = {
        get { parametersOf(componentContext) }
    }

    private val navigation = StackNavigation<Configuration>()

    private val stack: Value<ChildStack<Configuration, Child>> = childStack(
        source = navigation,
        initialConfiguration = Main,
        handleBackButton = true,
        key = "Root",
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, Child>> = stack

    override val state: Flow<Theme> = rootStore.states.map { it.theme }

    override val headlampDialogState: Flow<HeadLampType> = rootStore.states.map { it.headLampType }

    override fun closeHeadlampDialog() {
        rootStore.accept(Intent.CloseDialog)
    }

    private fun child(
        configuration: Configuration,
        componentContext: ComponentContext,
    ) = when (configuration) {
        is Main -> Child.Main(main(componentContext))
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Main : Configuration()
    }
}