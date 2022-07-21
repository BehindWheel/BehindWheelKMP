package com.egoriku.grodnoroads.screen.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Page
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Pref
import com.egoriku.grodnoroads.screen.settings.faq.FaqScreen
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.DialogState
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.DialogState.ThemeDialog
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.SettingsState
import com.egoriku.grodnoroads.screen.settings.ui.AppSettings
import com.egoriku.grodnoroads.screen.settings.ui.MapEventsSettings
import com.egoriku.grodnoroads.screen.settings.ui.MapPreferencesSettings
import com.egoriku.grodnoroads.screen.settings.ui.common.SettingsItem
import com.egoriku.grodnoroads.screen.settings.ui.dialog.AppThemeDialog
import com.egoriku.grodnoroads.screen.settings.whatsnew.WhatsNewScreen

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun SettingsUi(settingsComponent: SettingsComponent) {
    val settingsState by settingsComponent.settingsState.collectAsState(initial = SettingsState())
    val dialogState by settingsComponent.dialogState.collectAsState(initial = DialogState.None)

    DialogHandler(
        dialogState = dialogState,
        closeDialog = settingsComponent::closeDialog,
        processResult = settingsComponent::processResult
    )

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        Column {
            val childStack by settingsComponent.childStack.subscribeAsState()

            Children(
                modifier = Modifier.statusBarsPadding(),
                stack = childStack,
                animation = stackAnimation(slide())
            ) { created ->
                when (val child = created.instance) {
                    is SettingsComponent.Child.Settings -> d(settingsComponent)
                    is SettingsComponent.Child.FAQ -> FaqScreen(
                        faqComponent = child.faqComponent,
                        onBack = settingsComponent::onBack
                    )
                    is SettingsComponent.Child.Alerts -> TODO()
                    is SettingsComponent.Child.Appearance -> TODO()
                    is SettingsComponent.Child.BetaFeatures -> TODO()
                    is SettingsComponent.Child.Map -> TODO()
                    is SettingsComponent.Child.Markers -> TODO()
                    is SettingsComponent.Child.WhatsNew -> WhatsNewScreen(
                        whatsNewComponent = child.whatsNewComponent,
                        onBack = settingsComponent::onBack,
                    )
                }
            }

            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .verticalScroll(state = rememberScrollState())
            ) {

            }
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(state = rememberScrollState())
            ) {
                AppSettings(settingsState, settingsComponent::process)
                MapEventsSettings(
                    mapInfo = settingsState.mapInfo,
                    onCheckedChange = settingsComponent::onCheckedChanged
                )
                MapPreferencesSettings(
                    mapAppearance = settingsState.mapAppearance,
                    onCheckedChange = settingsComponent::onCheckedChanged
                )
            }
        }
    }
}

@Composable
fun d(settingsComponent: SettingsComponent) {
    Column {
        SettingsItem(
            icon = Icons.Filled.Style,
            text = stringResource(R.string.settings_section_appearance),
            onClick = {}
        )
        SettingsItem(
            icon = Icons.Filled.Info,
            text = stringResource(R.string.settings_section_markers),
            onClick = {}
        )
        SettingsItem(
            icon = Icons.Filled.Map,
            text = stringResource(R.string.settings_section_map),
            onClick = {}
        )
        SettingsItem(
            icon = Icons.Filled.NotificationImportant,
            text = stringResource(R.string.settings_section_alerts),
            onClick = {}
        )
        SettingsItem(
            icon = Icons.Filled.NewReleases,
            text = stringResource(R.string.settings_section_whats_new),
            onClick = {
                settingsComponent.open(page = Page.WhatsNew)
            }
        )
        SettingsItem(
            icon = Icons.Filled.FiberNew,
            text = stringResource(R.string.settings_section_beta_features),
            onClick = {}
        )
        SettingsItem(
            icon = Icons.Filled.Help,
            text = stringResource(R.string.settings_section_faq),
            onClick = {
                settingsComponent.open(page = Page.FAQ)
            }
        )
    }
}

@Composable
fun DialogHandler(
    dialogState: DialogState,
    closeDialog: () -> Unit,
    processResult: (Pref) -> Unit
) {
    when (dialogState) {
        is ThemeDialog -> {
            AppThemeDialog(
                dialogState = dialogState,
                closeDialog = closeDialog,
                processResult = processResult
            )
        }
        else -> {}
    }
}