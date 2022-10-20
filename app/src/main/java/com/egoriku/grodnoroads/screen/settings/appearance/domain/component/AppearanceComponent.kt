package com.egoriku.grodnoroads.screen.settings.appearance.domain.component

import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppLanguage
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppTheme
import com.egoriku.grodnoroads.screen.settings.appearance.domain.store.AppearanceStore
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Language
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Language.*
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme.*
import kotlinx.coroutines.flow.Flow

interface AppearanceComponent {

    val state: Flow<AppearanceStore.State>

    fun modify(preference: AppearancePref)
    fun update(preference: AppearancePref)
    fun closeDialog()

    data class AppearanceState(
        val appTheme: AppTheme = AppTheme(),
        val appLanguage: AppLanguage = AppLanguage(),
    )

    sealed interface AppearancePref {
        data class AppTheme(
            val current: Theme = System,
            val values: List<Theme> = listOf(System, Light, Dark)
        ) : AppearancePref

        data class AppLanguage(
            val current: Language = Russian,
            val values: List<Language> = listOf(Russian, Belarusian, English)
        ) : AppearancePref
    }

    sealed interface AppearanceDialogState {
        data class ThemeDialogState(val themes: AppTheme) : AppearanceDialogState
        data class LanguageDialogState(val languages: AppLanguage) : AppearanceDialogState

        object None : AppearanceDialogState
    }
}