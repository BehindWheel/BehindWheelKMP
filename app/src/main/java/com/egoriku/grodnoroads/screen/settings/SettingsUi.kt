package com.egoriku.grodnoroads.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.list.SettingsItem
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Page

@Composable
fun SettingsUi(onSettingClick: (Page) -> Unit) {
    Column {
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
        SettingsItem(
            icon = Icons.Filled.NewReleases,
            text = stringResource(R.string.settings_section_whats_new),
            onClick = {
                onSettingClick(Page.WhatsNew)
            }
        )
        SettingsItem(
            icon = Icons.Filled.Build,
            text = stringResource(R.string.settings_section_next_features),
            onClick = {
                onSettingClick(Page.NextFeatures)
            }
        )
        SettingsItem(
            icon = Icons.Filled.FiberNew,
            text = stringResource(R.string.settings_section_beta_features),
            onClick = {
                onSettingClick(Page.BetaFeatures)
            }
        )
        SettingsItem(
            icon = Icons.Filled.Help,
            text = stringResource(R.string.settings_section_faq),
            onClick = {
                onSettingClick(Page.FAQ)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingUiPreview() {
    SettingsUi(onSettingClick = {})
}