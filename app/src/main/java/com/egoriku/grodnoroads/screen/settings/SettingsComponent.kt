package com.egoriku.grodnoroads.screen.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.screen.settings.domain.Theme
import com.egoriku.grodnoroads.screen.settings.faq.FaqComponent
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.DialogState
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.SettingsState
import com.egoriku.grodnoroads.screen.settings.whatsnew.WhatsNewComponent
import kotlinx.coroutines.flow.Flow

interface SettingsComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class Settings(val componentContext: ComponentContext) : Child()
        data class Appearance(val componentContext: ComponentContext) : Child()
        data class Markers(val componentContext: ComponentContext) : Child()
        data class Map(val componentContext: ComponentContext) : Child()
        data class Alerts(val componentContext: ComponentContext) : Child()
        data class WhatsNew(val whatsNewComponent: WhatsNewComponent) : Child()
        data class BetaFeatures(val componentContext: ComponentContext) : Child()
        data class FAQ(val faqComponent: FaqComponent) : Child()
    }

    enum class Page {
        Appearance,
        Markers,
        Map,
        Alerts,
        WhatsNew,
        NextFeatures,
        BetaFeatures,
        FAQ
    }

    val settingsState: Flow<SettingsState>
    val dialogState: Flow<DialogState>


    fun onCheckedChanged(preference: Pref)

    fun process(preference: Pref)

    fun processResult(preference: Pref)

    fun closeDialog()

    fun open(page: Page)
    fun onBack()

    sealed interface Pref {
        data class AppTheme(
            val current: Theme = Theme.System,
            val values: List<Theme> = listOf(Theme.System, Theme.Light, Theme.Dark)
        ) : Pref

        data class StationaryCameras(val isShow: Boolean = true) : Pref
        data class MobileCameras(val isShow: Boolean = true) : Pref
        data class TrafficPolice(val isShow: Boolean = true) : Pref
        data class RoadIncident(val isShow: Boolean = true) : Pref
        data class TrafficJam(val isShow: Boolean = true) : Pref
        data class WildAnimals(val isShow: Boolean = true) : Pref
        data class CarCrash(val isShow: Boolean = true) : Pref

        data class TrafficJamAppearance(val isShow: Boolean = false) : Pref

    }
}