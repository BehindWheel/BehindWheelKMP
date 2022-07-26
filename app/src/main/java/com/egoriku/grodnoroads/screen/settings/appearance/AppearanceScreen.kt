package com.egoriku.grodnoroads.screen.settings.appearance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Language
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.list.MoreActionSettings
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.LanguageDialogState
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.ThemeDialogState
import com.egoriku.grodnoroads.screen.settings.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Language.Companion.toStringResource
import com.egoriku.grodnoroads.screen.settings.appearance.domain.model.Theme.Companion.toStringResource
import com.egoriku.grodnoroads.screen.settings.appearance.domain.store.AppearanceStore.State
import com.egoriku.grodnoroads.screen.settings.appearance.ui.dialog.AppLanguageDialog
import com.egoriku.grodnoroads.screen.settings.appearance.ui.dialog.AppThemeDialog

@Composable
fun AppearanceScreen(
    appearanceComponent: AppearanceComponent,
    onBack: () -> Unit
) {
    val state by appearanceComponent.state.collectAsState(initial = State())

    DialogHandler(
        dialogState = state.dialogState,
        onClose = appearanceComponent::closeDialog,
        onResult = appearanceComponent::update
    )
    Scaffold(
        topBar = {
            SettingsTopBar(
                title = stringResource(id = R.string.settings_section_appearance),
                onBack = onBack
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AppTheme(state = state, appearanceComponent = appearanceComponent)
            Language(state = state, appearanceComponent = appearanceComponent)
        }
    }
}

@Composable
private fun Language(
    state: State,
    appearanceComponent: AppearanceComponent
) {
    val language = state.appearanceState.appLanguage

    MoreActionSettings(
        icon = Icons.Default.Language,
        text = stringResource(R.string.appearance_app_language),
        value = stringResource(id = language.current.toStringResource()),
    ) {
        appearanceComponent.modify(language)
    }
}

@Composable
private fun AppTheme(
    state: State,
    appearanceComponent: AppearanceComponent
) {
    val appTheme = state.appearanceState.appTheme

    MoreActionSettings(
        icon = Icons.Default.DarkMode,
        text = stringResource(R.string.appearance_app_theme),
        value = stringResource(id = appTheme.current.toStringResource()),
    ) {
        appearanceComponent.modify(appTheme)
    }
}

@Composable
private fun DialogHandler(
    dialogState: AppearanceDialogState,
    onClose: () -> Unit,
    onResult: (AppearancePref) -> Unit
) {
    when (dialogState) {
        is ThemeDialogState -> {
            AppThemeDialog(
                themeDialogState = dialogState,
                onClose = onClose,
                onResult = onResult
            )
        }
        is LanguageDialogState -> {
            AppLanguageDialog(
                languageDialogState = dialogState,
                onClose = onClose,
                onResult = onResult
            )
        }
        else -> {}
    }
}