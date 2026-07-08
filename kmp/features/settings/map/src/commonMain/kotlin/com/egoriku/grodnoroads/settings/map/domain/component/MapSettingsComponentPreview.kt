package com.egoriku.grodnoroads.settings.map.domain.component

import com.egoriku.grodnoroads.extensions.coroutines.stateFlowOf
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapPref
import com.egoriku.grodnoroads.settings.map.domain.component.MapSettingsComponent.MapSettingState
import kotlinx.coroutines.flow.StateFlow

class MapSettingsComponentPreview : MapSettingsComponent {

    override val state: StateFlow<MapSettingState> = stateFlowOf {
        MapSettingState(isLoading = false)
    }

    override fun modify(preference: MapPref) = Unit
    override fun reset(preference: MapPref) = Unit
    override fun openDialog(preference: MapPref) = Unit
    override fun closeDialog() = Unit
}
