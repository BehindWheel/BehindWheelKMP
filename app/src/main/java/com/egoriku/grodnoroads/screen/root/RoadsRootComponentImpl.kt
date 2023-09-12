package com.egoriku.grodnoroads.screen.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.extensions.common.StateData
import com.egoriku.grodnoroads.screen.main.buildMainComponent
import com.egoriku.grodnoroads.screen.root.RoadsRootComponent.Child
import com.egoriku.grodnoroads.screen.root.store.RootStore
import com.egoriku.grodnoroads.screen.root.store.RootStoreFactory.Intent
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.setting.alerts.domain.component.buildAlertsComponent
import com.egoriku.grodnoroads.setting.appearance.domain.component.buildAppearanceComponent
import com.egoriku.grodnoroads.setting.faq.domain.component.buildFaqComponent
import com.egoriku.grodnoroads.setting.map.domain.component.buildMapSettingsComponent
import com.egoriku.grodnoroads.setting.whatsnew.domain.component.buildWhatsNewComponent
import com.egoriku.grodnoroads.shared.appcomponent.Page
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme
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

    override fun open(page: Page) {
        when (page) {
            Page.Appearance -> navigation.push(Config.Appearance)
            Page.Map -> navigation.push(Config.MapSettings)
            Page.Alerts -> navigation.push(Config.Alerts)
            Page.WhatsNew -> navigation.push(Config.WhatsNew)
            Page.NextFeatures -> navigation.push(Config.NextFeatures)
            Page.FAQ -> navigation.push(Config.FAQ)
        }
    }

    override fun onBack() = navigation.pop()

    private fun child(config: Config, componentContext: ComponentContext) = when (config) {
        is Config.Main -> Child.Main(
            component = buildMainComponent(
                componentContext = componentContext,
                onOpen = ::open
            )
        )

        is Config.Appearance -> Child.Appearance(
            appearanceComponent = buildAppearanceComponent(componentContext)
        )

        is Config.Alerts -> Child.Alerts(alertsComponent = buildAlertsComponent(componentContext))
        is Config.MapSettings -> Child.Map(
            mapSettingsComponent = buildMapSettingsComponent(componentContext)
        )

        is Config.NextFeatures -> TODO()
        is Config.WhatsNew -> Child.WhatsNew(
            whatsNewComponent = buildWhatsNewComponent(componentContext)
        )

        is Config.FAQ -> Child.FAQ(faqComponent = buildFaqComponent(componentContext))
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Main : Config()

        @Serializable
        data object Appearance : Config()

        @Serializable
        data object MapSettings : Config()

        @Serializable
        data object Alerts : Config()

        @Serializable
        data object WhatsNew : Config()

        @Serializable
        data object NextFeatures : Config()

        @Serializable
        data object FAQ : Config()
    }
}