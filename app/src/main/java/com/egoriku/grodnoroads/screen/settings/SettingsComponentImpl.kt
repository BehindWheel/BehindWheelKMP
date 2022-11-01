package com.egoriku.grodnoroads.screen.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Child
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Child.*
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Child.Map
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Page
import com.egoriku.grodnoroads.screen.settings.alerts.domain.component.AlertsComponentImpl
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponentImpl
import com.egoriku.grodnoroads.screen.settings.map.domain.component.MapSettingsComponentImpl
import com.egoriku.grodnoroads.screen.settings.whatsnew.component.WhatsNewComponentImpl
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class SettingsComponentImpl(
    componentContext: ComponentContext
) : SettingsComponent, KoinComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Settings,
        handleBackButton = true,
        key = "Settings",
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, Child>> = stack

    override fun open(page: Page) {
        when (page) {
            Page.Appearance -> navigation.push(Config.Appearance)
            Page.Map -> navigation.push(Config.Map)
            Page.Alerts -> navigation.push(Config.Alerts)
            Page.WhatsNew -> navigation.push(Config.WhatsNew)
            Page.NextFeatures -> navigation.push(Config.NextFeatures)
            Page.FAQ -> navigation.push(Config.FAQ)
        }
    }

    override fun onBack() = navigation.pop()

    private fun child(
        configuration: Config,
        componentContext: ComponentContext,
    ) = when (configuration) {
        is Config.Settings -> Settings

        is Config.Appearance -> Appearance(
            appearanceComponent = AppearanceComponentImpl(componentContext)
        )
        is Config.Alerts -> Alerts(
            alertsComponent = AlertsComponentImpl(componentContext)
        )
        is Config.Map -> Map(
            mapSettingsComponent = MapSettingsComponentImpl(componentContext)
        )
        is Config.NextFeatures -> TODO()
        is Config.WhatsNew -> WhatsNew(
            whatsNewComponent = WhatsNewComponentImpl(componentContext)
        )
        is Config.FAQ -> FAQ(
            faqComponent = get { parametersOf(componentContext) }
        )
    }

    private sealed class Config : Parcelable {
        @Parcelize
        object Settings : Config()

        @Parcelize
        object Appearance : Config()

        @Parcelize
        object Map : Config()

        @Parcelize
        object Alerts : Config()

        @Parcelize
        object WhatsNew : Config()

        @Parcelize
        object NextFeatures : Config()

        @Parcelize
        object FAQ : Config()
    }
}