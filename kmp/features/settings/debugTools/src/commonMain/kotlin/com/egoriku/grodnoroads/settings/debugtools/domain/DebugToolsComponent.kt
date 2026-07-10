package com.egoriku.grodnoroads.settings.debugtools.domain

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.StateFlow

@Stable
interface DebugToolsComponent {

    val showMapDebugOverlay: StateFlow<Boolean>

    fun resetOnboarding()
    fun resetReportingLimit()
    fun setShowMapDebugOverlay(enabled: Boolean)
}
