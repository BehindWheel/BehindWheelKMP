package com.egoriku.grodnoroads.quicksettings.domain.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.AppTheme
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.MarkerFiltering
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.TrafficJamOnMap
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.VoiceAlerts

@Stable
data class QuickSettingsState(
    val appTheme: AppTheme = AppTheme(),
    val markerFiltering: MarkerFiltering = MarkerFiltering(),
    val trafficJamOnMap: TrafficJamOnMap = TrafficJamOnMap(),
    val voiceAlerts: VoiceAlerts = VoiceAlerts()
)