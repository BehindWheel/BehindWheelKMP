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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibility
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.screen.settings.whatsnew.store.WhatsNewStore

@OptIn(ExperimentalMaterialApi::class)
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
                    Card(
                        onClick = {},
                        elevation = 3.dp,
                        shape = RoundedCornerShape(10.dp),
                        contentColor = when (index) {
                            0 -> MaterialTheme.colors.secondary.copy(alpha = 0.5f)
                            else -> contentColorFor(MaterialTheme.colors.surface)
                        },
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 16.dp)
                        ) {
                            Text(text = releaseNotes.versionName, fontWeight = FontWeight.Bold)
                            Text(text = releaseNotes.notes)
                        }
                    }
                }
            }
        }
    }
}
