package com.egoriku.grodnoroads.settings.appearance.domain.component

import androidx.compose.runtime.Stable
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppLanguage
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppTheme
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.KeepScreenOn
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.MapTypeAppearance
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.State
import com.egoriku.grodnoroads.settings.appearance.domain.util.isBYLocaleSupported
import com.egoriku.grodnoroads.shared.persistent.appearance.Language
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme
import com.egoriku.grodnoroads.shared.persistent.map.mapstyle.MapType
import kotlinx.coroutines.flow.StateFlow

@Stable
interface AppearanceComponent {

    val state: StateFlow<State>

    fun modify(preference: AppearancePref)
    fun update(preference: AppearancePref)
    fun closeDialog()

    @Stable
    data class AppearanceState(
        val appTheme: AppTheme = AppTheme(),
        val appLanguage: AppLanguage = AppLanguage(),
        val mapTypeAppearance: MapTypeAppearance = MapTypeAppearance(),
        val keepScreenOn: KeepScreenOn = KeepScreenOn()
    )

    @Stable
    sealed interface AppearancePref {
        data class AppTheme(
            val current: Theme = Theme.System,
            val values: List<Theme> = listOf(Theme.System, Theme.Light, Theme.Dark, Theme.Auto)
        ) : AppearancePref

        data class AppLanguage(
            val current: Language = Language.Russian,
            val values: List<Language> = buildList {
                add(Language.System)
                add(Language.Russian)

                if (isBYLocaleSupported()) {
                    add(Language.Belarusian)
                }
                add(Language.English)
            }
        ) : AppearancePref

        data class MapTypeAppearance(
            val current: MapType = MapType.Normal,
            val values: List<MapType> = MapType.entries
        ) : AppearancePref

        data class KeepScreenOn(val enabled: Boolean = false) : AppearancePref
    }

    @Stable
    sealed interface AppearanceDialogState {
        data class ThemeDialogState(val themes: AppTheme) : AppearanceDialogState
        data class LanguageDialogState(val languages: AppLanguage) : AppearanceDialogState

        data object None : AppearanceDialogState
    }
}
