package com.egoriku.grodnoroads.settings.debugtools.domain.datastore

import com.egoriku.grodnoroads.extensions.coroutines.stateFlowOf
import kotlinx.coroutines.flow.StateFlow

class DataStoreEditComponentPreview : DataStoreEditComponent {

    override val showMapDebugOverlay: StateFlow<Boolean> = stateFlowOf { false }

    override fun setShowMapDebugOverlay(enabled: Boolean) = Unit
    override fun resetOnboarding() = Unit
    override fun resetReportingLimit() = Unit
}
