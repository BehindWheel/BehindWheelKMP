package com.egoriku.grodnoroads.quicksettings.domain.model

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.*

@Stable
data class QuickSettingsState(
    val appTheme: AppTheme = AppTheme(),
    val markerFiltering: MarkerFiltering = MarkerFiltering(),
    val trafficJamOnMap: TrafficJamOnMap = TrafficJamOnMap(),
    val voiceAlerts: VoiceAlerts = VoiceAlerts()
)