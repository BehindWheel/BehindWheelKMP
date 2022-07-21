package com.egoriku.grodnoroads.screen.settings.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.topbar.SettingsTopBar

@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            SettingsTopBar(
                title = stringResource(id = R.string.settings_section_about),
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
            Question(text = stringResource(id = R.string.settings_about_q1))
            Answer(text = stringResource(id = R.string.settings_about_a1))

            Spacer(modifier = Modifier.height(16.dp))

            Question(text = stringResource(id = R.string.settings_about_q2))
            Answer(text = stringResource(id = R.string.settings_about_a2))
        }
    }
}

@Composable
private fun Question(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun Answer(text: String) {
    Text(
        modifier = Modifier.padding(top = 4.dp),
        text = text,
        style = MaterialTheme.typography.subtitle1
    )
}