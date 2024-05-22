package com.egoriku.grodnoroads.appsettings.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.appsettings.domain.AppSettingsComponent
import com.egoriku.grodnoroads.appsettings.domain.AppSettingsComponentPreview
import com.egoriku.grodnoroads.appsettings.screen.ui.section.PrivacyPolicySection
import com.egoriku.grodnoroads.appsettings.screen.ui.section.SocialNetworkSection
import com.egoriku.grodnoroads.appsettings.screen.ui.section.VersionSection
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.ic_appearance
import com.egoriku.grodnoroads.compose.resources.ic_changelog
import com.egoriku.grodnoroads.compose.resources.ic_faq
import com.egoriku.grodnoroads.compose.resources.ic_map
import com.egoriku.grodnoroads.compose.resources.ic_notification_badge
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.WeightSpacer
import com.egoriku.grodnoroads.localization.R
import com.egoriku.grodnoroads.shared.models.Page
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.tab_settings),
                    style = MaterialTheme.typography.headlineSmall
                )
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
                        text = stringResource(R.string.settings_category_main)
                    )
                    SettingsListItem(
                        drawableResource = Res.drawable.ic_appearance,
                        name = stringResource(R.string.settings_section_appearance),
                        paddingValues = PaddingValues(horizontal = 20.dp),
                        onClick = { onSettingClick(Page.Appearance) }
                    )
                    SettingsListItem(
                        drawableResource = Res.drawable.ic_map,
                        name = stringResource(R.string.settings_section_map),
                        paddingValues = PaddingValues(horizontal = 20.dp),
                        onClick = { onSettingClick(Page.MapSettings) }
                    )
                    SettingsListItem(
                        drawableResource = Res.drawable.ic_notification_badge,
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
                        drawableResource = Res.drawable.ic_changelog,
                        name = stringResource(R.string.settings_section_changelog),
                        paddingValues = PaddingValues(horizontal = 20.dp),
                        onClick = { onSettingClick(Page.Changelog) }
                    )
                    SettingsListItem(
                        drawableResource = Res.drawable.ic_faq,
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
    drawableResource: DrawableResource,
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
            painter = painterResource(drawableResource),
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
