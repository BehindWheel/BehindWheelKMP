package com.egoriku.grodnoroads.settings.appearance.domain.component

import com.egoriku.grodnoroads.extensions.coroutines.stateFlowOf
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.State
import kotlinx.coroutines.flow.StateFlow

class AppearanceComponentPreview : AppearanceComponent {

    override val state: StateFlow<State> = stateFlowOf { State() }

    override fun modify(preference: AppearanceComponent.AppearancePref) = Unit
    override fun update(preference: AppearanceComponent.AppearancePref) = Unit
    override fun closeDialog() = Unit
}
