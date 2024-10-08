package com.egoriku.grodnoroads.onboarding.ui.foundation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.uikit.VerticalSpacer
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun InfographicPage(
    resource: DrawableResource,
    title: String,
    description: String
) {
    Column {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            painter = painterResource(resource),
            contentDescription = null
        )
        Column(modifier = Modifier.padding(horizontal = 30.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
            VerticalSpacer(16.dp)
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
