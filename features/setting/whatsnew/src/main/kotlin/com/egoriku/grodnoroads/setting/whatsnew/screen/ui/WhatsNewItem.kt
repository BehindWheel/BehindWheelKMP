package com.egoriku.grodnoroads.setting.whatsnew.screen.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.setting.whatsnew.domain.model.ReleaseNotes

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun WhatsNewItem(
    isLatestRelease: Boolean,
    release: ReleaseNotes
) {
    Card(
        onClick = {},
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            val versionName = when {
                isLatestRelease -> "${release.versionName} \uD83C\uDD95"
                else -> release.versionName
            }
            Text(text = versionName, fontWeight = FontWeight.Bold)
            Text(
                text = release.releaseDate,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = release.notes)
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