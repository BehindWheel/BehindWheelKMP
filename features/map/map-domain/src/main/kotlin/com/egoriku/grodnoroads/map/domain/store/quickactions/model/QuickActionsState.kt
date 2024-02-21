package com.egoriku.grodnoroads.map.domain.store.quickactions.model

import com.egoriku.grodnoroads.map.domain.store.quickactions.model.QuickActionsPref.*

data class QuickActionsState(
    val appTheme: AppTheme = AppTheme(),
    val markerFiltering: MarkerFiltering = MarkerFiltering(),
    val trafficJamOnMap: TrafficJamOnMap = TrafficJamOnMap(),
    val voiceAlerts: VoiceAlerts = VoiceAlerts()
)