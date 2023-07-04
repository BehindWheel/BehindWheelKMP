package com.egoriku.grodnoroads.setting.appearance.screen.bottomsheet

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.lazycolumn.SingleChoiceLazyColumn
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.bottomsheet.BasicModalBottomSheet
import com.egoriku.grodnoroads.foundation.uikit.bottomsheet.common.ConfirmationFooter
import com.egoriku.grodnoroads.foundation.uikit.bottomsheet.rememberSheetCloseBehaviour
import com.egoriku.grodnoroads.setting.appearance.domain.component.AppearanceComponent.AppearanceDialogState.LanguageDialogState
import com.egoriku.grodnoroads.setting.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Language.Companion.toStringResource
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppLanguageBottomSheet(
    languageDialogState: LanguageDialogState,
    onCancel: () -> Unit,
    onResult: (AppearancePref) -> Unit
) {
    var language by rememberMutableState { languageDialogState.languages }

    val sheetCloseBehaviour = rememberSheetCloseBehaviour(
        onCancel = onCancel,
        onResult = { onResult(language) }
    )

    BasicModalBottomSheet(
        sheetState = sheetCloseBehaviour.sheetState,
        onCancel = onCancel,
        content = {
            SingleChoiceLazyColumn(
                list = language.values.map {
                    stringResource(id = it.toStringResource())
                }.toImmutableList(),
                contentPadding = PaddingValues(bottom = 16.dp),
                initialSelection = language.values.indexOf(language.current),
                onSelected = { position ->
                    language = language.copy(current = language.values[position])
                }
            )
            Divider()
        },
        footer = {
            ConfirmationFooter(
                onDismiss = sheetCloseBehaviour::cancel,
                onAccept = sheetCloseBehaviour::hideWithResult
            )
        }
    )
}

@GrodnoRoadsPreview
@Composable
private fun PreviewAppLanguageBottomSheet() = GrodnoRoadsM3ThemePreview {
    AppLanguageBottomSheet(
        languageDialogState = LanguageDialogState(languages = AppearancePref.AppLanguage()),
        onCancel = { },
        onResult = {}
    )
}