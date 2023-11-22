package com.egoriku.grodnoroads.compose.snackbar.ui.internal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.Simple
import com.egoriku.grodnoroads.compose.snackbar.ui.core.SnackbarSurface
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsM3ThemePreview

@Composable
internal fun SimpleMessageItem(simple: Simple) {
    SnackbarSurface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = simple.title,
            )
            if (!simple.description.isNullOrEmpty()) {
                // TODO: create foundation-core-ui and use disabled text
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = simple.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalContentColor.current.copy(alpha = 0.38f)
                )
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun SimpleMessageItemPreview() = GrodnoRoadsM3ThemePreview {
    Box(modifier = Modifier.padding(16.dp)) {
        SimpleMessageItem(
            simple = Simple(
                title = "Доступ к геолокации запрещен.",
                description = "Используются для доступа к данным карт",
            )
        )
    }
}