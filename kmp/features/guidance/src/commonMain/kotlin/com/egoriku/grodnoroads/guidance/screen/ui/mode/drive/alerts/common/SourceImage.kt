package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.colored.App
import com.egoriku.grodnoroads.foundation.icons.colored.AppDark
import com.egoriku.grodnoroads.foundation.icons.colored.Telegram
import com.egoriku.grodnoroads.foundation.icons.colored.Viber
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import com.egoriku.grodnoroads.foundation.theme.isLight
import com.egoriku.grodnoroads.shared.models.MessageSource

@Composable
fun SourceImage(
    messageSource: MessageSource,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier,
        imageVector = when (messageSource) {
            MessageSource.Viber -> GrodnoRoads.Colored.Viber
            MessageSource.Telegram -> GrodnoRoads.Colored.Telegram
            MessageSource.App -> {
                when {
                    MaterialTheme.colorScheme.isLight -> GrodnoRoads.Colored.App
                    else -> GrodnoRoads.Colored.AppDark
                }
            }
        },
        contentDescription = "Source App"
    )
}

@PreviewGrodnoRoads
@Composable
private fun PreviewSourceImagePreview() = GrodnoRoadsM3ThemePreview {
    Column {
        SourceImage(messageSource = MessageSource.App)
        SourceImage(messageSource = MessageSource.Viber)
        SourceImage(messageSource = MessageSource.Telegram)
    }
}
