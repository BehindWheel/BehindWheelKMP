package com.egoriku.grodnoroads.settings.debugtools.domain.datastore

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.StateFlow

@Stable
interface DataStoreEditComponent {

    val showMapDebugOverlay: StateFlow<Boolean>

    fun setShowMapDebugOverlay(enabled: Boolean)
    fun resetOnboarding()
    fun resetReportingLimit()
}
