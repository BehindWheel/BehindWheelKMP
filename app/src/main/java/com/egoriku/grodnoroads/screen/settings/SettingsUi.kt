package com.egoriku.grodnoroads.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Style
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibility
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibilityState.SHOWN
import com.egoriku.grodnoroads.foundation.list.SettingsItem
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Page
import com.egoriku.grodnoroads.screen.settings.ui.VersionSection
import com.egoriku.grodnoroads.settings.root.ui.PrivacyPolicySection
import com.egoriku.grodnoroads.settings.root.ui.SocialNetworkSection

@Composable
fun SettingsUi(
    appVersion: String,
    onSettingClick: (Page) -> Unit
) {
    BottomBarVisibility(SHOWN)

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
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
            // Temporary disable
            /*SettingsItem(
                icon = Icons.Filled.NotificationImportant,
                text = stringResource(R.string.settings_section_alerts),
                onClick = {
                    onSettingClick(Page.Alerts)
                }
            )*/
            SettingsItem(
                icon = Icons.Filled.NewReleases,
                text = stringResource(R.string.settings_section_whats_new),
                onClick = {
                    onSettingClick(Page.WhatsNew)
                }
            )
            // Temporary disable
            /*SettingsItem(
                icon = Icons.Filled.Build,
                text = stringResource(R.string.settings_section_next_features),
                onClick = {
                    onSettingClick(Page.NextFeatures)
                }
            )*/
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
            Divider()
            PrivacyPolicySection()
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun SettingUiPreview() = GrodnoRoadsTheme {
    SettingsUi(onSettingClick = {}, appVersion = "1.0.0")
}
