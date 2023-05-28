package com.egoriku.grodnoroads.settings.root.screen.ui.foundation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R

@Composable
fun SocialNetwork(
    title: String,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedButton(
            onClick = onClick,
            modifier = Modifier.size(50.dp),
            contentPadding = PaddingValues(0.dp),
        ) {
            icon()
        }
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
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
                painter = painterResource(id = R.drawable.ic_telegram_logo_minimal),
                contentDescription = null
            )
        }
    }
}