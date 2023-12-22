package com.egoriku.grodnoroads.settings.appearance.domain.component

import com.egoriku.grodnoroads.coroutines.CStateFlow
import com.egoriku.grodnoroads.coroutines.stateFlowOf
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.State

class AppearanceComponentPreview : AppearanceComponent {

    override val state: CStateFlow<State>
        get() = stateFlowOf { State() }

    override fun modify(preference: AppearanceComponent.AppearancePref) = Unit
    override fun update(preference: AppearanceComponent.AppearancePref) = Unit
    override fun closeDialog() = Unit
}