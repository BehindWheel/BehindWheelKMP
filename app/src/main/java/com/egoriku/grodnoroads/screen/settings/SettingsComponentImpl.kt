package com.egoriku.grodnoroads.screen.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Child
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Page
import com.egoriku.grodnoroads.screen.settings.faq.component.FaqComponentImpl
import com.egoriku.grodnoroads.screen.settings.store.SettingsStore
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.*
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.Intent.OnCheckedChanged
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.Intent.ProcessPreferenceClick
import com.egoriku.grodnoroads.screen.settings.whatsnew.component.WhatsNewComponentImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.parcelize.Parcelize
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class SettingsComponentImpl(
    componentContext: ComponentContext
) : SettingsComponent, KoinComponent, ComponentContext by componentContext {

    private val settingsStore = instanceKeeper.getStore { get<SettingsStore>() }

    private val navigation = StackNavigation<Config>()

    private val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Settings,
        handleBackButton = true,
        key = "Settings",
        childFactory = ::child
    )

    override val childStack: Value<ChildStack<*, Child>> = stack

    override val settingsState: Flow<SettingsState> = settingsStore.states.map {
        it.settingsState
    }

    override val dialogState: Flow<DialogState> = settingsStore.states.map {
        it.dialogState
    }

    override fun onCheckedChanged(preference: SettingsComponent.Pref) {
        settingsStore.accept(OnCheckedChanged(preference))
    }

    override fun process(preference: SettingsComponent.Pref) {
        settingsStore.accept(ProcessPreferenceClick(preference = preference))
    }

    override fun processResult(preference: SettingsComponent.Pref) {
        settingsStore.accept(Intent.ProcessDialogResult(preference = preference))
    }

    override fun closeDialog() {
        settingsStore.accept(Intent.CloseDialog)
    }

    override fun open(page: Page) {
        when (page) {
            Page.Appearance -> navigation.push(Config.Appearance)
            Page.Markers -> navigation.push(Config.Markers)
            Page.Map -> navigation.push(Config.Map)
            Page.Alerts -> navigation.push(Config.Alerts)
            Page.WhatsNew -> navigation.push(Config.WhatsNew)
            Page.NextFeatures -> navigation.push(Config.NextFeatures)
            Page.BetaFeatures -> navigation.push(Config.BetaFeatures)
            Page.FAQ -> navigation.push(Config.FAQ)
        }
    }

    override fun onBack() = navigation.pop()

    private fun child(
        configuration: Config,
        componentContext: ComponentContext,
    ) = when (configuration) {
        is Config.Settings -> Child.Settings(componentContext)
        is Config.WhatsNew -> Child.WhatsNew(
            whatsNewComponent = WhatsNewComponentImpl(
                componentContext = componentContext
            )
        )
        is Config.FAQ -> Child.FAQ(
            faqComponent = FaqComponentImpl(
                componentContext = componentContext
            )
        )
        Config.Alerts -> TODO()
        Config.Appearance -> TODO()
        Config.BetaFeatures -> TODO()
        Config.Map -> TODO()
        Config.Markers -> TODO()
        Config.NextFeatures -> TODO()
    }

    private sealed class Config : Parcelable {
        @Parcelize
        object Settings : Config()

        @Parcelize
        object Appearance : Config()

        @Parcelize
        object Markers : Config()

        @Parcelize
        object Map : Config()

        @Parcelize
        object Alerts : Config()

        @Parcelize
        object WhatsNew : Config()

        @Parcelize
        object NextFeatures : Config()

        @Parcelize
        object BetaFeatures : Config()

        @Parcelize
        object FAQ : Config()
    }
}