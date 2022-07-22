package com.egoriku.grodnoroads.screen.settings.appearance.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.foundation.dialog.ListSingleChoiceDialog
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.LanguageDialogState
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppLanguage
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Language.Companion.toStringResource

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

@Preview
@Preview(locale = "ru")
@Composable
private fun PreviewLanguageDialog() {
    AppLanguageDialog(
        languageDialogState = LanguageDialogState(languages = AppLanguage()),
        onClose = {},
        onResult = {}
    )
}