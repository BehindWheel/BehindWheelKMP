package com.egoriku.grodnoroads.appsettings.screen.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.app_version
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import org.jetbrains.compose.resources.stringResource

@Composable
fun VersionSection(
    appVersion: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(Res.string.app_version, appVersion),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@PreviewGrodnoRoads
@Composable
private fun PreviewVersionSectionPreview() = GrodnoRoadsM3ThemePreview {
    VersionSection("1.0.0")
}
