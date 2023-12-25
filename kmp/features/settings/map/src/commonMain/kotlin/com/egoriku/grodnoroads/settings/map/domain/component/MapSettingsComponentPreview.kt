package com.egoriku.grodnoroads.settings.map.domain.component

import com.egoriku.grodnoroads.coroutines.CFlow
import com.egoriku.grodnoroads.coroutines.stateFlowOf
import com.egoriku.grodnoroads.settings.map.domain.store.MapSettingsStore

class MapSettingsComponentPreview : MapSettingsComponent {
    override val mapSettingsState: CFlow<MapSettingsComponent.MapSettingState>
        get() = stateFlowOf {
            MapSettingsComponent.MapSettingState(
                isLoading = false
            )
        }

    override fun modify(preference: MapSettingsComponent.MapPref) {}

    override fun reset(preference: MapSettingsComponent.MapPref) {}

    override fun openDialog(preference: MapSettingsComponent.MapPref) {}

    override fun closeDialog() {}
}