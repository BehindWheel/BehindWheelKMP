package com.egoriku.grodnoroads.quicksettings.domain.component

import com.egoriku.grodnoroads.quicksettings.domain.model.QuickSettingsState
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref
import kotlinx.coroutines.flow.Flow

interface QuickSettingsComponent {

    val quickSettingsState: Flow<QuickSettingsState>

    fun updatePreferences(pref: QuickSettingsPref)
}