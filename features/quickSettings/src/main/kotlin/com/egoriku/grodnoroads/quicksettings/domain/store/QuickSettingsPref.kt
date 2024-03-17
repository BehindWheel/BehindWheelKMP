package com.egoriku.grodnoroads.quicksettings.domain.store

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme
import com.egoriku.grodnoroads.shared.appsettings.types.map.filtering.Filtering
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Stable
sealed interface QuickSettingsPref {
    @Stable
    data class AppTheme(
        val current: Theme = Theme.System,
        val values: ImmutableList<Theme> = Theme.entries.toImmutableList()
    ) : QuickSettingsPref

    @Stable
    data class MarkerFiltering(
        val current: Filtering = Filtering.Hours1,
        val values: List<Filtering> = Filtering.entries
    ) : QuickSettingsPref

    @Stable
    data class TrafficJamOnMap(val isShow: Boolean = false) : QuickSettingsPref

    @Stable
    data class VoiceAlerts(val enabled: Boolean = true) : QuickSettingsPref
}