package com.egoriku.grodnoroads.screen.main.ui.drawer.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.BuildConfig
import com.egoriku.grodnoroads.R

@Composable
fun VersionSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.drawer_app_version, BuildConfig.VERSION_NAME),
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview
@Composable
fun PreviewVersionSection() {
    VersionSection()
}