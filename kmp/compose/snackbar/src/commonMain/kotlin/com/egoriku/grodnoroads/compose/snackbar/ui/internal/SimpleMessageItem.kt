package com.egoriku.grodnoroads.compose.snackbar.ui.internal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.compose.snackbar.model.Icon
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData.Raw
import com.egoriku.grodnoroads.compose.snackbar.model.MessageData.StringRes
import com.egoriku.grodnoroads.compose.snackbar.model.SnackbarMessage.SimpleMessage
import com.egoriku.grodnoroads.compose.snackbar.ui.core.SnackbarSurface
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.CheckCircle
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsDarkLightPreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.uikit.DisabledText
import org.jetbrains.compose.resources.stringResource

@Composable
fun SimpleMessageItem(message: SimpleMessage) {
    SnackbarSurface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val icon = message.icon
                if (icon != null) {
                    when (icon) {
                        is Icon.DrawableRes -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null
                        )
                        is Icon.Vector -> Icon(
                            imageVector = icon.imageVector,
                            contentDescription = null
                        )
                    }
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = when (val title = message.title) {
                        is Raw -> title.text
                        is StringRes -> stringResource(title.resource)
                    }
                )
            }
            val description = message.description
            if (description != null) {
                DisabledText(
                    modifier = Modifier.fillMaxWidth(),
                    text = when (description) {
                        is Raw -> description.text
                        is StringRes -> stringResource(description.resource)
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.inverseOnSurface,
                )
            }
        }
    }
}

@GrodnoRoadsDarkLightPreview
@Composable
private fun SimpleMessageItemPreview() = GrodnoRoadsM3ThemePreview {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SimpleMessageItem(
            message = SimpleMessage(
                title = Raw("Доступ к геолокации запрещен."),
                description = Raw("Используется для доступа к данным карт"),
            )
        )
        SimpleMessageItem(message = SimpleMessage(title = Raw("Доступ к геолокации запрещен.")))
        SimpleMessageItem(
            message = SimpleMessage(
                title = Raw("Доступ к геолокации запрещен."),
                icon = Icon.DrawableRes(imageVector = GrodnoRoads.Outlined.CheckCircle)
            )
        )
    }
}