package com.egoriku.grodnoroads.settings.root.domain.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent
import com.egoriku.grodnoroads.settings.faq.domain.component.FaqComponent
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent
import com.egoriku.grodnoroads.settings.root.domain.model.Page
import com.egoriku.grodnoroads.settings.whatsnew.domain.component.WhatsNewComponent

interface SettingsComponent {

    val childStack: Value<ChildStack<*, Child>>

    val appVersion: String

    sealed class Child {
        data class Settings(val settingsComponent: SettingsComponent) : Child()

        data class Appearance(val appearanceComponent: AppearanceComponent) : Child()
        data class Map(val mapSettingsComponent: MapSettingsComponent) : Child()
        data class Alerts(val alertsComponent: AlertsComponent) : Child()
        data class WhatsNew(val whatsNewComponent: WhatsNewComponent) : Child()
        data class NextFeatures(val componentContext: ComponentContext) : Child()
        data class BetaFeatures(val componentContext: ComponentContext) : Child()
        data class FAQ(val faqComponent: FaqComponent) : Child()
    }

    fun open(page: Page)
    fun onBack()
}