package com.egoriku.grodnoroads.settings.whatsnew.screen.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.settings.whatsnew.domain.model.ReleaseNotes

@Composable
@OptIn(ExperimentalMaterialApi::class)
internal fun WhatsNewItem(
    isLatestRelease: Boolean,
    release: ReleaseNotes
) {
    Card(
        onClick = {},
        elevation = 3.dp,
        shape = RoundedCornerShape(10.dp),
        contentColor = when {
            isLatestRelease -> MaterialTheme.colors.secondary.copy(alpha = 0.5f)
            else -> contentColorFor(MaterialTheme.colors.surface)
        },
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
                modifier = Modifier.alpha(ContentAlpha.medium),
                style = MaterialTheme.typography.caption
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = release.notes)
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun WhatsNewItemPreview(@PreviewParameter(LoremIpsum::class) lorem: String) {
    GrodnoRoadsTheme {
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