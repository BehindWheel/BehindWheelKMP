package com.egoriku.grodnoroads.mainflow.domain

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.egoriku.grodnoroads.settings.alerts.domain.component.AlertsComponent
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent
import com.egoriku.grodnoroads.settings.changelog.domain.component.ChangelogComponent
import com.egoriku.grodnoroads.settings.debugtools.domain.DebugToolsComponent
import com.egoriku.grodnoroads.settings.faq.domain.component.FaqComponent
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent
import kotlinx.coroutines.flow.StateFlow

@Stable
interface MainFlowComponent : BackHandlerOwner {

    val childStack: StateFlow<ChildStack<*, Child>>
    fun onBack()

    sealed class Child {
        data class Tabs(val component: TabsComponent) : Child()

        data class Alerts(val component: AlertsComponent) : Child()
        data class Appearance(val component: AppearanceComponent) : Child()
        data class Changelog(val component: ChangelogComponent) : Child()
        data class DebugTools(val component: DebugToolsComponent) : Child()
        data class FAQ(val component: FaqComponent) : Child()
        data class MapSettings(val component: MapSettingsComponent) : Child()
    }
}
