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
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.ic_telegram
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.uikit.button.SecondaryCircleButton
import org.jetbrains.compose.resources.painterResource

@Composable
fun SocialNetwork(
    title: String,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SecondaryCircleButton(onClick = onClick, content = content)
        Text(
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.bodySmall,
            text = title
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun SocialNetworkPreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.padding(32.dp)) {
        SocialNetwork(title = "Channel", onClick = {}) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 2.dp),
                painter = painterResource(Res.drawable.ic_telegram),
                contentDescription = null
            )
        }
    }
}