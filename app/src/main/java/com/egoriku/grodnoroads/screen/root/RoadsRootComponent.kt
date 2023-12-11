package com.egoriku.grodnoroads.screen.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.egoriku.grodnoroads.extensions.common.StateData
import com.egoriku.grodnoroads.screen.main.MainComponent
import com.egoriku.grodnoroads.screen.root.store.headlamp.HeadLampType
import com.egoriku.grodnoroads.setting.alerts.domain.component.AlertsComponent
import com.egoriku.grodnoroads.setting.appearance.domain.component.AppearanceComponent
import com.egoriku.grodnoroads.setting.faq.domain.component.FaqComponent
import com.egoriku.grodnoroads.setting.map.domain.component.MapSettingsComponent
import com.egoriku.grodnoroads.setting.changelog.domain.component.ChangelogComponent
import com.egoriku.grodnoroads.shared.appcomponent.Page
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme
import kotlinx.coroutines.flow.Flow

interface RoadsRootComponent {

    val childStack: Value<ChildStack<*, Child>>

    val themeState: Flow<StateData<Theme>>

    val headlampDialogState: Flow<HeadLampType>

    fun closeHeadlampDialog()

    fun open(page: Page)
    fun onBack()

    sealed class Child {
        data class Main(val component: MainComponent) : Child()
        data class Appearance(val appearanceComponent: AppearanceComponent) : Child()
        data class Map(val mapSettingsComponent: MapSettingsComponent) : Child()
        data class Alerts(val alertsComponent: AlertsComponent) : Child()
        data class WhatsNew(val changelogComponent: ChangelogComponent) : Child()
        data class NextFeatures(val componentContext: ComponentContext) : Child()
        data class BetaFeatures(val componentContext: ComponentContext) : Child()
        data class FAQ(val faqComponent: FaqComponent) : Child()
    }
}