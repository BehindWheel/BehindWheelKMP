package com.egoriku.grodnoroads.settings.whatsnew.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibility
import com.egoriku.grodnoroads.foundation.bottombar.BottomBarVisibilityState.HIDDEN
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.settings.whatsnew.domain.component.WhatsNewComponent
import com.egoriku.grodnoroads.settings.whatsnew.domain.store.WhatsNewStore
import com.egoriku.grodnoroads.settings.whatsnew.screen.ui.WhatsNewItem

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
