package com.egoriku.grodnoroads.settings.appearance.domain.component

import com.egoriku.grodnoroads.coroutines.CStateFlow
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.*
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.State
import com.egoriku.grodnoroads.settings.appearance.domain.util.isBYLocaleSupported
import com.egoriku.grodnoroads.shared.persistent.appearance.Language
import com.egoriku.grodnoroads.shared.persistent.appearance.Theme

interface AppearanceComponent {

    val state: CStateFlow<State>

    fun modify(preference: AppearancePref)
    fun update(preference: AppearancePref)
    fun closeDialog()

    data class AppearanceState(
        val appTheme: AppTheme = AppTheme(),
        val appLanguage: AppLanguage = AppLanguage(),
        val keepScreenOn: KeepScreenOn = KeepScreenOn()
    )

    sealed interface AppearancePref {
        data class AppTheme(
            val current: Theme = Theme.System,
            val values: List<Theme> = listOf(Theme.System, Theme.Light, Theme.Dark)
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

        data class KeepScreenOn(val enabled: Boolean = false) : AppearancePref
    }

    sealed interface AppearanceDialogState {
        data class ThemeDialogState(val themes: AppTheme) : AppearanceDialogState
        data class LanguageDialogState(val languages: AppLanguage) : AppearanceDialogState

        data object None : AppearanceDialogState
    }
}