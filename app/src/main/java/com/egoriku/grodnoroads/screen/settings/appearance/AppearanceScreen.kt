package com.egoriku.grodnoroads.screen.settings.appearance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar

@Composable
fun AppearanceScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            SettingsTopBar(
                title = stringResource(id = R.string.settings_section_appearance),
                onBack = onBack
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
        }
    }
}