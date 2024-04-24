package com.egoriku.grodnoroads.quicksettings.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.coroutines.flow.CFlow
import com.egoriku.grodnoroads.quicksettings.domain.model.QuickSettingsState
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref

@Stable
interface QuickSettingsComponent {

    val quickSettingsState: CFlow<QuickSettingsState>

    fun updatePreferences(pref: QuickSettingsPref)
}