package com.egoriku.grodnoroads.screen.settings.whatsnew

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibility
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.screen.settings.whatsnew.store.WhatsNewStore
import com.egoriku.grodnoroads.screen.settings.whatsnew.store.WhatsNewStore.State.ReleaseNotes

@Composable
fun WhatsNewScreen(
    whatsNewComponent: WhatsNewComponent,
    onBack: () -> Unit
) {
    BottomBarVisibility(HIDDEN)

    Scaffold(
        topBar = {
            SettingsTopBar(
                title = stringResource(id = R.string.settings_section_whats_new),
                onBack = onBack
            )
        }
    ) { paddingValues ->
        val state by whatsNewComponent.state.collectAsState(initial = WhatsNewStore.State())

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(state.releaseNotes) { index, releaseNotes ->
                    WhatsNewItem(isLatestRelease = index == 0, release = releaseNotes)
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
private fun WhatsNewItem(
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