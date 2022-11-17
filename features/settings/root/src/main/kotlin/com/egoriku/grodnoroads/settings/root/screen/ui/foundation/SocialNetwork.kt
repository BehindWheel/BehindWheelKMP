package com.egoriku.grodnoroads.settings.root.screen.ui.foundation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
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
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
        ) {
            icon()
        }

        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(top = 8.dp),
            style = MaterialTheme.typography.caption,
            text = title
        )
    }
}

@GrodnoRoadsPreview
@Composable
private fun SocialNetworkPreview() {
    GrodnoRoadsTheme {

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