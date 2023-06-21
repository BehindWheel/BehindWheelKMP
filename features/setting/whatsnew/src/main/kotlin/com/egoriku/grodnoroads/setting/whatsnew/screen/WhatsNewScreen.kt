package com.egoriku.grodnoroads.setting.whatsnew.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar
import com.egoriku.grodnoroads.resources.R
import com.egoriku.grodnoroads.setting.whatsnew.domain.component.WhatsNewComponent
import com.egoriku.grodnoroads.setting.whatsnew.domain.store.WhatsNewStore
import com.egoriku.grodnoroads.setting.whatsnew.screen.ui.WhatsNewItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WhatsNewScreen(
    whatsNewComponent: WhatsNewComponent,
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            SettingsTopBar(
                scrollBehavior = scrollBehavior,
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
                contentPadding = WindowInsets
                    .navigationBars
                    .add(WindowInsets(left = 16.dp, right = 16.dp, top = 16.dp, bottom = 16.dp))
                    .asPaddingValues(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                itemsIndexed(state.releaseNotes) { index, releaseNotes ->
                    WhatsNewItem(isLatestRelease = index == 0, release = releaseNotes)
                }
            }
        }
    }
}