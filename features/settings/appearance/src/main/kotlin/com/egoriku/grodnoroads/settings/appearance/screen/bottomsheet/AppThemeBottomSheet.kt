package com.egoriku.grodnoroads.settings.appearance.screen.bottomsheet

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.lazycolumn.SingleChoiceLazyColumn
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.bottomsheet.BasicModalBottomSheet
import com.egoriku.grodnoroads.foundation.uikit.bottomsheet.rememberSheetCloseBehaviour
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.ThemeDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppTheme
import com.egoriku.grodnoroads.settings.appearance.screen.bottomsheet.common.ConfirmationFooter
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme.Companion.toStringResource
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppThemeBottomSheet(
    themeDialogState: ThemeDialogState,
    onCancel: () -> Unit,
    onResult: (AppearancePref) -> Unit
) {
    var theme by rememberMutableState { themeDialogState.themes }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val sheetCloseBehaviour = rememberSheetCloseBehaviour(
        sheetState = { sheetState },
        onCancel = onCancel,
        onResult = {
            onResult(theme)
        }
    )

    BasicModalBottomSheet(
        sheetState = sheetState,
        onCancel = onCancel,
        content = {
            SingleChoiceLazyColumn(
                list = theme.values.map {
                    stringResource(id = it.toStringResource())
                }.toImmutableList(),
                contentPadding = PaddingValues(bottom = 16.dp),
                initialSelection = theme.values.indexOf(theme.current),
                onSelected = { position ->
                    theme = theme.copy(current = theme.values[position])
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
fun PreviewAppThemeBottomSheet() = GrodnoRoadsM3ThemePreview {
    AppThemeBottomSheet(
        themeDialogState = ThemeDialogState(themes = AppTheme()),
        onCancel = {},
        onResult = {}
    )
}