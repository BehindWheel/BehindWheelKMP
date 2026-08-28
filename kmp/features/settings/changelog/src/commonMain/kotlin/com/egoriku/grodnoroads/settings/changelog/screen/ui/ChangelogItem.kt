package com.egoriku.grodnoroads.settings.changelog.screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.extensions.LoremIpsum
import com.egoriku.grodnoroads.foundation.core.rememberMutableState
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.More
import com.egoriku.grodnoroads.foundation.layout.Spacer
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.DisabledText
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogEntry
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogPlatform
import com.egoriku.grodnoroads.shared.formatter.ChangelogFormatter

private val latestReleaseEmojis = listOf("🔥", "⭐", "🎉", "✨", "🚀", "💎")

@Composable
internal fun ChangelogItem(
    isLatestRelease: Boolean,
    release: ChangelogEntry,
    allowedModify: Boolean = false,
    onEditClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Card {
        Box {
            if (allowedModify) {
                ChangelogItemMenu(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                val versionName = when {
                    isLatestRelease -> {
                        val emoji = remember { latestReleaseEmojis.random() }
                        release.latestReleaseBadge(emoji)
                    }
                    else -> release.versionName
                }
                Text(
                    text = versionName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                DisabledText(
                    text = ChangelogFormatter.format(release.releaseDateMillis),
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(4.dp)
                Text(
                    text = release.notes,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun ChangelogItemMenu(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by rememberMutableState { false }

    Box(modifier = modifier) {
        IconButton(onClick = { expanded = true }) {
            Icon(imageVector = GrodnoRoads.Outlined.More, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Edit") },
                onClick = {
                    expanded = false
                    onEditClick()
                }
            )
            DropdownMenuItem(
                text = { Text("Delete") },
                onClick = {
                    expanded = false
                    onDeleteClick()
                }
            )
        }
    }
}

@PreviewGrodnoRoadsDarkLight
@Composable
private fun ChangelogPreview() = GrodnoRoadsM3ThemePreview {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ChangelogItem(
            isLatestRelease = true,
            allowedModify = true,
            release = ChangelogEntry(
                id = "1",
                platform = ChangelogPlatform.Android,
                versionName = "1.0.2",
                notes = LoremIpsum.generateLoremIpsum(10),
                releaseDateMillis = 1653004800000L
            )
        )
        ChangelogItem(
            isLatestRelease = false,
            release = ChangelogEntry(
                id = "2",
                platform = ChangelogPlatform.Android,
                versionName = "1.0.1",
                notes = LoremIpsum.generateLoremIpsum(20),
                releaseDateMillis = 1653868800000L
            )
        )
    }
}
