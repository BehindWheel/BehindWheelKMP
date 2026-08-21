package com.egoriku.grodnoroads.settings.changelog.screen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.extensions.LoremIpsum
import com.egoriku.grodnoroads.foundation.layout.Spacer
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoadsDarkLight
import com.egoriku.grodnoroads.foundation.uikit.DisabledText
import com.egoriku.grodnoroads.settings.changelog.domain.model.ChangelogEntry

private val latestReleaseEmojis = listOf("🔥", "⭐", "🎉", "✨", "🚀", "💎")

@Composable
internal fun ChangelogItem(
    isLatestRelease: Boolean,
    release: ChangelogEntry
) {
    Card {
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
                text = release.releaseDate,
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
            release = ChangelogEntry(
                versionName = "1.0.2",
                notes = LoremIpsum.generateLoremIpsum(10),
                releaseDate = "20.05.2022"
            )
        )
        ChangelogItem(
            isLatestRelease = false,
            release = ChangelogEntry(
                versionName = "1.0.1",
                notes = LoremIpsum.generateLoremIpsum(20),
                releaseDate = "30.05.2022"
            )
        )
    }
}
