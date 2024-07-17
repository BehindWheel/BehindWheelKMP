package com.egoriku.grodnoroads.settings.appearance.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.appearance_app_theme
import com.egoriku.grodnoroads.compose.resources.appearance_keep_screen_on
import com.egoriku.grodnoroads.compose.resources.appearance_keep_screen_on_description
import com.egoriku.grodnoroads.compose.resources.settings_category_other
import com.egoriku.grodnoroads.compose.resources.settings_section_appearance
import com.egoriku.grodnoroads.foundation.common.ui.SettingsSectionHeader
import com.egoriku.grodnoroads.foundation.common.ui.SettingsTopBar
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Brightness
import com.egoriku.grodnoroads.foundation.icons.outlined.Moon
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import com.egoriku.grodnoroads.foundation.uikit.listitem.MoreActionListItem
import com.egoriku.grodnoroads.foundation.uikit.listitem.SwitchListItem
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.LanguageDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearanceDialogState.ThemeDialogState
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppLanguage
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.AppTheme
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponent.AppearancePref.KeepScreenOn
import com.egoriku.grodnoroads.settings.appearance.domain.component.AppearanceComponentPreview
import com.egoriku.grodnoroads.settings.appearance.domain.store.AppearanceStore.State
import com.egoriku.grodnoroads.settings.appearance.screen.bottomsheet.AppLanguageBottomSheet
import com.egoriku.grodnoroads.settings.appearance.screen.bottomsheet.AppThemeBottomSheet
import com.egoriku.grodnoroads.shared.persistent.toStringResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceScreen(
    appearanceComponent: AppearanceComponent,
    onBack: () -> Unit
) {
    val state by appearanceComponent.state.collectAsState(initial = State())
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    DialogHandler(
        dialogState = state.dialogState,
        onClose = appearanceComponent::closeDialog,
        onResult = appearanceComponent::update
    )
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            SettingsTopBar(
                title = stringResource(Res.string.settings_section_appearance),
                onBack = onBack,
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            AppThemeSection(state = state, onModify = appearanceComponent::modify)
            LanguageSection(state = state, onModify = appearanceComponent::modify)
            VerticalSpacer(16.dp)
            SettingsSectionHeader(title = stringResource(Res.string.settings_category_other))
            KeepScreenOnSettings(state = state, onModify = appearanceComponent::update)
        }
    }
}

@Composable
expect fun LanguageSection(
    state: State,
    onModify: (AppLanguage) -> Unit
)

@Composable
private fun AppThemeSection(
    state: State,
    onModify: (AppTheme) -> Unit,
) {
    val appTheme = state.appearanceState.appTheme

    MoreActionListItem(
        imageVector = GrodnoRoads.Outlined.Moon,
        text = stringResource(Res.string.appearance_app_theme),
        value = stringResource(appTheme.current.toStringResource()),
        onClick = { onModify(appTheme) },
    )
}

@Composable
private fun KeepScreenOnSettings(
    state: State,
    onModify: (KeepScreenOn) -> Unit
) {
    val keepScreenOn = state.appearanceState.keepScreenOn

    SwitchListItem(
        imageVector = GrodnoRoads.Outlined.Brightness,
        text = stringResource(Res.string.appearance_keep_screen_on),
        description = stringResource(Res.string.appearance_keep_screen_on_description),
        isChecked = keepScreenOn.enabled,
        onCheckedChange = {
            onModify(keepScreenOn.copy(enabled = it))
        }
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
            AppThemeBottomSheet(
                themeDialogState = dialogState,
                onCancel = onClose,
                onResult = onResult
            )
        }

        is LanguageDialogState -> {
            AppLanguageBottomSheet(
                languageDialogState = dialogState,
                onCancel = onClose,
                onResult = {
                    onClose()
                    onResult(it)
                }
            )
        }

        else -> {}
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun AppearanceScreenPreview() = GrodnoRoadsM3ThemePreview {
    AppearanceScreen(
        appearanceComponent = AppearanceComponentPreview(),
        onBack = {}
    )
}