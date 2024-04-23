package com.egoriku.grodnoroads.quicksettings.domain.model

import com.egoriku.grodnoroads.quicksettings.domain.store.QuickSettingsPref.*

data class QuickSettingsState(
    val appTheme: AppTheme = AppTheme(),
    val markerFiltering: MarkerFiltering = MarkerFiltering(),
    val trafficJamOnMap: TrafficJamOnMap = TrafficJamOnMap(),
    val voiceAlerts: VoiceAlerts = VoiceAlerts()
)