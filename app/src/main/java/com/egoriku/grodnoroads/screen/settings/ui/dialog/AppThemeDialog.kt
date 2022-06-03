package com.egoriku.grodnoroads.screen.settings.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.foundation.dialog.ListSingleChoiceDialog
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Pref
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Pref.AppTheme
import com.egoriku.grodnoroads.screen.settings.domain.Theme.Companion.toStringResource
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.DialogState.ThemeDialog

@Composable
fun AppThemeDialog(
    dialogState: ThemeDialog,
    closeDialog: () -> Unit,
    processResult: (Pref) -> Unit
) {
    val theme = dialogState.preference

    ListSingleChoiceDialog(
        list = theme.values.map {
            stringResource(id = it.toStringResource())
        },
        initialSelection = theme.values.indexOf(theme.current),
        onClose = closeDialog,
        onSelected = { position ->
            processResult(theme.copy(current = theme.values[position]))
        }
    )
}

@Preview
@Preview(locale = "ru")
@Composable
fun PreviewAppThemeDialog() {
    AppThemeDialog(
        dialogState = ThemeDialog(preference = AppTheme()),
        closeDialog = {},
        processResult = {}
    )
}