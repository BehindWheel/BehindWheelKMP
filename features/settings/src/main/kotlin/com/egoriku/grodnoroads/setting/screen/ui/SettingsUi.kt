package com.egoriku.grodnoroads.setting.screen.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.SettingsHeader
import com.egoriku.grodnoroads.foundation.list.SettingsItem
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.screen.ui.section.PrivacyPolicySection
import com.egoriku.grodnoroads.setting.screen.ui.section.SocialNetworkSection
import com.egoriku.grodnoroads.setting.screen.ui.section.VersionSection
import com.egoriku.grodnoroads.shared.appcomponent.FeatureFlags.settingsNextFeaturesEnabled
import com.egoriku.grodnoroads.shared.appcomponent.Page

@Composable
internal fun SettingsUi(
    appVersion: String,
    onSettingClick: (Page) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .padding(top = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SettingsHeader(
                title = stringResource(R.string.settings_category_main),
                top = 0.dp
            )
            SettingsItem(
                icon = Icons.Filled.Style,
                text = stringResource(R.string.settings_section_appearance),
                onClick = {
                    onSettingClick(Page.Appearance)
                }
            )
            SettingsItem(
                icon = Icons.Filled.Map,
                text = stringResource(R.string.settings_section_map),
                onClick = {
                    onSettingClick(Page.Map)
                }
            )
            SettingsItem(
                icon = Icons.Filled.NotificationImportant,
                text = stringResource(R.string.settings_section_alerts),
                onClick = {
                    onSettingClick(Page.Alerts)
                }
            )

            SettingsHeader(title = stringResource(R.string.settings_category_other))
            SettingsItem(
                icon = Icons.Filled.NewReleases,
                text = stringResource(R.string.settings_section_whats_new),
                onClick = {
                    onSettingClick(Page.WhatsNew)
                }
            )

            if (settingsNextFeaturesEnabled) {
                SettingsItem(
                    icon = Icons.Filled.Build,
                    text = stringResource(R.string.settings_section_next_features),
                    onClick = {
                        onSettingClick(Page.NextFeatures)
                    }
                )
            }
            SettingsItem(
                icon = Icons.Filled.Help,
                text = stringResource(R.string.settings_section_faq),
                onClick = {
                    onSettingClick(Page.FAQ)
                }
            )

            Spacer(modifier = Modifier.weight(1f))
            SocialNetworkSection()
            VersionSection(appVersion = appVersion)
            PrivacyPolicySection()
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun SettingUiPreview() = GrodnoRoadsM3ThemePreview {
    SettingsUi(onSettingClick = {}, appVersion = "1.0.0")
}
