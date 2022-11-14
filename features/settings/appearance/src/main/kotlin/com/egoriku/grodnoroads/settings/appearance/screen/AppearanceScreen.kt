package com.egoriku.grodnoroads.settings.appearance.screen

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
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibility
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.egoriku.grodnoroads.foundation.list.MoreActionSettings
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.LanguageDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.ThemeDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppLanguage
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppTheme
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.State
import com.egoriku.grodnoroads.settings.appearance.screen.dialog.AppLanguageDialog
import com.egoriku.grodnoroads.settings.appearance.screen.dialog.AppThemeDialog
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Language.Companion.toStringResource
import com.egoriku.grodnoroads.shared.appsettings.types.appearance.Theme.Companion.toStringResource

@Composable
fun AppearanceScreen(
    appearanceComponent: AppearanceComponent,
    onBack: () -> Unit
) {
    BottomBarVisibility(HIDDEN)

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
            AppThemeSection(state = state, onModify = appearanceComponent::modify)
            LanguageSection(state = state, onModify = appearanceComponent::modify)
        }
    }
}

@Composable
private fun LanguageSection(
    state: State,
    onModify: (AppLanguage) -> Unit
) {
    val language = state.appearanceState.appLanguage

    MoreActionSettings(
        icon = Icons.Default.Language,
        text = stringResource(R.string.appearance_app_language),
        value = stringResource(id = language.current.toStringResource()),
        onClick = { onModify(language) },
    )
}

@Composable
private fun AppThemeSection(
    state: State,
    onModify: (AppTheme) -> Unit,
) {
    val appTheme = state.appearanceState.appTheme

    MoreActionSettings(
        icon = Icons.Default.DarkMode,
        text = stringResource(R.string.appearance_app_theme),
        value = stringResource(id = appTheme.current.toStringResource()),
        onClick = { onModify(appTheme) },
    )
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