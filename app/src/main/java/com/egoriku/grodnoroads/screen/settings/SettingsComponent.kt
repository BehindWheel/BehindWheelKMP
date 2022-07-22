package com.egoriku.grodnoroads.screen.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent
import com.egoriku.grodnoroads.screen.settings.faq.FaqComponent
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.SettingsState
import com.egoriku.grodnoroads.screen.settings.whatsnew.WhatsNewComponent
import kotlinx.coroutines.flow.Flow

interface SettingsComponent {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        object Settings : Child()

        data class Appearance(val appearanceComponent: AppearanceComponent) : Child()
        data class Map(val componentContext: ComponentContext) : Child()
        data class Alerts(val componentContext: ComponentContext) : Child()
        data class WhatsNew(val whatsNewComponent: WhatsNewComponent) : Child()
        data class NextFeatures(val componentContext: ComponentContext) : Child()
        data class BetaFeatures(val componentContext: ComponentContext) : Child()
        data class FAQ(val faqComponent: FaqComponent) : Child()
    }

    enum class Page {
        Appearance,
        Map,
        Alerts,
        WhatsNew,
        NextFeatures,
        BetaFeatures,
        FAQ
    }

    val settingsState: Flow<SettingsState>

    fun onCheckedChanged(preference: Pref)

    fun process(preference: Pref)

    fun processResult(preference: Pref)

    fun closeDialog()

    fun open(page: Page)
    fun onBack()

    sealed interface Pref {
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