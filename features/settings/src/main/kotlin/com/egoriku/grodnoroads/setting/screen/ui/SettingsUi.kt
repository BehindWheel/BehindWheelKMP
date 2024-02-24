package com.egoriku.grodnoroads.setting.screen.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.WeightSpacer
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.screen.ui.section.PrivacyPolicySection
import com.egoriku.grodnoroads.setting.screen.ui.section.SocialNetworkSection
import com.egoriku.grodnoroads.setting.screen.ui.section.VersionSection
import com.egoriku.grodnoroads.shared.appcomponent.FeatureFlags.settingsNextFeaturesEnabled
import com.egoriku.grodnoroads.shared.appcomponent.Page

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsUi(
    contentPadding: PaddingValues,
    appVersion: String,
    onSettingClick: (Page) -> Unit
) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 28.dp,
                            bottomEnd = 28.dp
                        )
                    ),
                title = {
                    Text(text = stringResource(R.string.tab_settings))
                }
            )
            Card(modifier = Modifier.padding(horizontal = 16.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        text = stringResource(R.string.settings_category_main)
                    )
                    SettingsListItem(
                        icon = R.drawable.ic_appearance,
                        name = stringResource(R.string.settings_section_appearance),
                        paddingValues = PaddingValues(horizontal = 20.dp),
                        onClick = { onSettingClick(Page.Appearance) }
                    )
                    SettingsListItem(
                        icon = R.drawable.ic_map,
                        name = stringResource(R.string.settings_section_map),
                        paddingValues = PaddingValues(horizontal = 20.dp),
                        onClick = { onSettingClick(Page.Map) }
                    )
                    SettingsListItem(
                        icon = R.drawable.ic_notification,
                        name = stringResource(R.string.settings_section_alerts),
                        paddingValues = PaddingValues(horizontal = 20.dp),
                        onClick = { onSettingClick(Page.Alerts) }
                    )
                }
            }
            Card(modifier = Modifier.padding(horizontal = 16.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        text = stringResource(R.string.settings_category_other)
                    )
                    SettingsListItem(
                        icon = R.drawable.ic_changelog,
                        name = stringResource(R.string.settings_section_changelog),
                        paddingValues = PaddingValues(horizontal = 20.dp),
                        onClick = { onSettingClick(Page.Changelog) }
                    )
                    if (settingsNextFeaturesEnabled) {
                        SettingsListItem(
                            icon = Icons.Filled.Build,
                            name = stringResource(R.string.settings_section_next_features),
                            paddingValues = PaddingValues(horizontal = 20.dp),
                            onClick = { onSettingClick(Page.NextFeatures) }
                        )
                    }
                    SettingsListItem(
                        icon = R.drawable.ic_faq,
                        name = stringResource(R.string.settings_section_faq),
                        paddingValues = PaddingValues(horizontal = 20.dp),
                        onClick = { onSettingClick(Page.FAQ) }
                    )
                }
            }
            WeightSpacer()
            SocialNetworkSection()
            VersionSection(appVersion = appVersion)
            PrivacyPolicySection()
        }
    }
}

@Composable
private fun SettingsListItem(
    icon: ImageVector,
    name: String,
    paddingValues: PaddingValues = PaddingValues(),
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .sizeIn(minHeight = 44.dp)
            .clickable(onClick = onClick)
            .padding(paddingValues),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Text(text = name, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun SettingsListItem(
    icon: Int,
    name: String,
    paddingValues: PaddingValues = PaddingValues(),
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 44.dp)
            .clickable(onClick = onClick)
            .padding(paddingValues),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null
        )
        Text(text = name, style = MaterialTheme.typography.titleMedium)
    }
}

@GrodnoRoadsPreview
@Composable
private fun SettingUiPreview() = GrodnoRoadsM3ThemePreview {
    SettingsUi(
        contentPadding = PaddingValues(),
        onSettingClick = {},
        appVersion = "1.0.0"
    )
}
