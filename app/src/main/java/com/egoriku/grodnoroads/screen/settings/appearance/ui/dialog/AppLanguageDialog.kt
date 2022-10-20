package com.egoriku.grodnoroads.screen.settings.appearance.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.dialog.ListSingleChoiceDialog
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.LanguageDialogState
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppLanguage
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Language.Companion.toStringResource

@Composable
fun AppLanguageDialog(
    languageDialogState: LanguageDialogState,
    onClose: () -> Unit,
    onResult: (AppearancePref) -> Unit
) {
    val language = languageDialogState.languages

    ListSingleChoiceDialog(
        list = language.values.map {
            stringResource(id = it.toStringResource())
        },
        initialSelection = language.values.indexOf(language.current),
        onClose = onClose,
        onSelected = { position ->
            onResult(language.copy(current = language.values[position]))
        }
    )
}

@GrodnoRoadsPreview
@Composable
private fun PreviewLanguageDialog() {
    GrodnoRoadsTheme {
        AppLanguageDialog(
            languageDialogState = LanguageDialogState(languages = AppLanguage()),
            onClose = {},
            onResult = {}
        )
    }
}