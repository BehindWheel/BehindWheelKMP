package com.egoriku.grodnoroads.settings.appearance.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.appearance_app_language
import com.egoriku.grodnoroads.compose.resources.ic_language
import com.egoriku.grodnoroads.extensions.openAppSettings
import com.egoriku.grodnoroads.foundation.uikit.listitem.MoreActionListItem
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore
import com.egoriku.grodnoroads.shared.persistent.appearance.Language
import com.egoriku.grodnoroads.shared.persistent.toStringResource
import org.jetbrains.compose.resources.stringResource
import platform.Foundation.NSBundle

@Composable
actual fun LanguageSection(
    state: AppearanceStore.State,
    onModify: (AppearanceComponent.AppearancePref.AppLanguage) -> Unit
) {
    val language = remember {
        val code = NSBundle.mainBundle.preferredLocalizations.firstOrNull()
        when (code) {
            "ru" -> Language.Russian
            "be" -> Language.Belarusian
            "en" -> Language.English
            else -> Language.System
        }
    }

    MoreActionListItem(
        drawableResource = Res.drawable.ic_language,
        text = stringResource(Res.string.appearance_app_language),
        value = stringResource(language.toStringResource()),
        onClick = ::openAppSettings,
    )
}