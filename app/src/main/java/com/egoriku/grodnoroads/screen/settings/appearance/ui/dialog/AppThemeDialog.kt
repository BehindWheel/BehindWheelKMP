package com.egoriku.grodnoroads.screen.settings.appearance.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.dialog.ListSingleChoiceDialog
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.ThemeDialogState
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppTheme
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme.Companion.toStringResource

@Composable
fun AppThemeDialog(
    themeDialogState: ThemeDialogState,
    onClose: () -> Unit,
    onResult: (AppearancePref) -> Unit
) {
    val theme = themeDialogState.themes

    ListSingleChoiceDialog(
        list = theme.values.map {
            stringResource(id = it.toStringResource())
        },
        initialSelection = theme.values.indexOf(theme.current),
        onClose = onClose,
        onSelected = { position ->
            onResult(theme.copy(current = theme.values[position]))
        }
    )
}

@GrodnoRoadsPreview
@Composable
fun PreviewAppThemeDialog() {
    GrodnoRoadsTheme {
        AppThemeDialog(
            themeDialogState = ThemeDialogState(themes = AppTheme()),
            onClose = {},
            onResult = {}
        )
    }
}