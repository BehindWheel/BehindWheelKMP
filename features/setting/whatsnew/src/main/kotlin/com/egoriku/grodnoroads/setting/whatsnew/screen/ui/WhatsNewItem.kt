package com.egoriku.grodnoroads.setting.whatsnew.screen.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.DisabledText
import com.egoriku.grodnoroads.setting.whatsnew.domain.model.ReleaseNotes

@Composable
internal fun WhatsNewItem(
    isLatestRelease: Boolean,
    release: ReleaseNotes
) {
    Card(shape = RoundedCornerShape(10.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            val versionName = when {
                isLatestRelease -> "${release.versionName} \uD83D\uDD25"
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
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = release.notes,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun WhatsNewItemPreview(@PreviewParameter(LoremIpsum::class) lorem: String) {
    GrodnoRoadsM3ThemePreview {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            WhatsNewItem(
                isLatestRelease = true,
                release = ReleaseNotes(
                    versionCode = 1000,
                    versionName = "1000",
                    notes = lorem.take(100),
                    releaseDate = "20.05.2022"
                )
            )
            WhatsNewItem(
                isLatestRelease = false,
                release = ReleaseNotes(
                    versionCode = 2000,
                    versionName = "2000",
                    notes = lorem.take(200),
                    releaseDate = "30.05.2022"
                )
            )
        }
    }
}