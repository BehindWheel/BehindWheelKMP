package com.egoriku.grodnoroads.quicksettings.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.quicksettings.domain.model.QuickSettingsState
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref
import kotlinx.coroutines.flow.StateFlow

@Stable
interface QuickSettingsComponent {

    val quickSettingsState: StateFlow<QuickSettingsState>

    fun updatePreferences(pref: QuickSettingsPref)
}
