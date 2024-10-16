package com.egoriku.grodnoroads.appsettings.screen.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Telegram
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.uikit.button.SecondaryCircleButton

@Composable
fun SocialNetwork(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SecondaryCircleButton(onClick = onClick, content = content)
        Text(
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodySmall,
            text = title
        )
    }
}

@PreviewGrodnoRoads
@Composable
private fun SocialNetworkPreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.padding(32.dp)) {
        SocialNetwork(title = "Channel", onClick = {}) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 2.dp),
                imageVector = GrodnoRoads.Outlined.Telegram,
                contentDescription = null
            )
        }
    }
}
