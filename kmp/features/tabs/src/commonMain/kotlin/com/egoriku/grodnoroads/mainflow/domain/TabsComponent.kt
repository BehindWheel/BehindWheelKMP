package com.egoriku.grodnoroads.mainflow.domain

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.egoriku.grodnoroads.appsettings.domain.component.AppSettingsComponent
import com.egoriku.grodnoroads.coroutines.flow.CStateFlow
import com.egoriku.grodnoroads.guidance.domain.component.GuidanceComponent
import com.egoriku.grodnoroads.shared.models.reporting.ReportParams

@Stable
interface TabsComponent {

    val childStack: CStateFlow<ChildStack<*, Child>>
    val childSlot: CStateFlow<ChildSlot<*, Any>>

    fun onSelectTab(index: Int)

    fun closeReporting()
    fun processReporting(params: ReportParams)

    sealed class Child(val index: Int) {
        data class Guidance(val component: GuidanceComponent) : Child(index = 0)
        data class AppSettings(val component: AppSettingsComponent) : Child(index = 1)
    }
}