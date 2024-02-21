package com.egoriku.grodnoroads.map.domain.store.quickactions.model

import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme
import com.egoriku.grodnoroads.shared.appsettings.types.map.filtering.Filtering
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

sealed interface QuickActionsPref {
    data class AppTheme(
        val current: Theme = Theme.System,
        val values: ImmutableList<Theme> = Theme.entries.toImmutableList()
    ) : QuickActionsPref

    data class MarkerFiltering(
        val current: Filtering = Filtering.Hours1,
        val values: List<Filtering> = Filtering.entries
    ) : QuickActionsPref

    data class TrafficJamOnMap(val isShow: Boolean = false) : QuickActionsPref
    data class VoiceAlerts(val enabled: Boolean = true) : QuickActionsPref
}