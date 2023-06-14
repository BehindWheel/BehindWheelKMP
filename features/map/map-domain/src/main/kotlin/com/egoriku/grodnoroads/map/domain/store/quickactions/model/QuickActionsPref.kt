package com.egoriku.grodnoroads.map.domain.store.quickactions.model

import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme

sealed interface QuickActionsPref {
    data class AppTheme(
        val current: Theme = Theme.System,
        val values: List<Theme> = listOf(Theme.System, Theme.Light, Theme.Dark)
    ) : QuickActionsPref
}