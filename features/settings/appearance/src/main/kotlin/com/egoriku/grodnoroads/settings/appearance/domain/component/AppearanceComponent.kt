package com.egoriku.grodnoroads.settings.appearance.domain.component

import android.os.Build
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.*
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.State
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Language
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme
import kotlinx.coroutines.flow.Flow

interface AppearanceComponent {

    val state: Flow<State>

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

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    add(Language.Belarusian)
                }
                add(Language.English)
            }
        ) : AppearancePref

        data class KeepScreenOn(
            val enabled: Boolean = false
        ): AppearancePref
    }

    sealed interface AppearanceDialogState {
        data class ThemeDialogState(val themes: AppTheme) : AppearanceDialogState
        data class LanguageDialogState(val languages: AppLanguage) : AppearanceDialogState

        object None : AppearanceDialogState
    }
}