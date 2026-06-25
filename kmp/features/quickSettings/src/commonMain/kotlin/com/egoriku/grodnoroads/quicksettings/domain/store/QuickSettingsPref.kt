package com.egoriku.grodnoroads.quicksettings.domain.store

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme
import com.egoriku.grodnoroads.shared.persistent.map.filtering.Filtering
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.MapType

@Stable
sealed interface QuickSettingsPref {
    @Stable
    data class AppTheme(
        val current: Theme = Theme.System,
        val values: List<Theme> = Theme.entries
    ) : QuickSettingsPref

    @Stable
    data class MarkerFiltering(
        val current: Filtering = Filtering.Minutes45,
        val values: List<Filtering> = Filtering.entries
    ) : QuickSettingsPref

    @Stable
    data class MapTypeAppearance(
        val current: MapType = MapType.Normal,
        val values: List<MapType> = MapType.entries
    ) : QuickSettingsPref

    @Stable
    data class TrafficJamOnMap(val isShow: Boolean = false) : QuickSettingsPref

    @Stable
    data class VoiceAlerts(val enabled: Boolean = true) : QuickSettingsPref
}
