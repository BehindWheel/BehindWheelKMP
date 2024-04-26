package com.egoriku.grodnoroads.appsettings.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.appsettings.domain.AppSettingsComponent
import com.egoriku.grodnoroads.appsettings.domain.AppSettingsComponentPreview
import com.egoriku.grodnoroads.appsettings.screen.ui.section.PrivacyPolicySection
import com.egoriku.grodnoroads.appsettings.screen.ui.section.SocialNetworkSection
import com.egoriku.grodnoroads.appsettings.screen.ui.section.VersionSection
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.WeightSpacer
import com.egoriku.grodnoroads.resources.MR
import com.egoriku.grodnoroads.resources.stringResource
import com.egoriku.grodnoroads.resources_old.R
import com.egoriku.grodnoroads.shared.models.Page

@Composable
fun AppSettingsScreen(
    contentPadding: PaddingValues,
    settingsComponent: AppSettingsComponent
) {
    SettingsUi(
        contentPadding = contentPadding,
        appVersion = settingsComponent.appVersion,
        onSettingClick = settingsComponent::open
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsUi(
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
                    Text(text = stringResource(MR.strings.tab_settings))
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
                        text = stringResource(MR.strings.settings_category_main)
                    )
                    SettingsListItem(
                        icon = R.drawable.ic_appearance,
                        name = stringResource(MR.strings.settings_section_appearance),
                        paddingValues = PaddingValues(horizontal = 20.dp),
                        onClick = { onSettingClick(Page.Appearance) }
                    )
                    SettingsListItem(
                        icon = MR.images.ic_map.drawableResId,
                        name = stringResource(MR.strings.settings_section_map),
                        paddingValues = PaddingValues(horizontal = 20.dp),
                        onClick = { onSettingClick(Page.MapSettings) }
                    )
                    SettingsListItem(
                        icon = R.drawable.ic_notification_badge,
                        name = stringResource(MR.strings.settings_section_alerts),
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
                        text = stringResource(MR.strings.settings_category_other)
                    )
                    SettingsListItem(
                        icon = R.drawable.ic_changelog,
                        name = stringResource(MR.strings.settings_section_changelog),
                        paddingValues = PaddingValues(horizontal = 20.dp),
                        onClick = { onSettingClick(Page.Changelog) }
                    )
                    SettingsListItem(
                        icon = R.drawable.ic_faq,
                        name = stringResource(MR.strings.settings_section_faq),
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
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            contentDescription = null
        )
        Text(text = name, style = MaterialTheme.typography.titleMedium)
    }
}

@GrodnoRoadsPreview
@Composable
private fun AppSettingsScreenPreview() = GrodnoRoadsM3ThemePreview {
    AppSettingsScreen(
        settingsComponent = AppSettingsComponentPreview(),
        contentPadding = PaddingValues()
    )
}
