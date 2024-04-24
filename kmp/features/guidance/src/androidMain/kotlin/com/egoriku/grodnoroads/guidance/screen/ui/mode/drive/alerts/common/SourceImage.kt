package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources_old.R
import com.egoriku.grodnoroads.shared.models.MessageSource

@Composable
fun SourceImage(
    modifier: Modifier = Modifier,
    messageSource: MessageSource
) {
    Image(
        modifier = modifier.size(24.dp),
        painter = when (messageSource) {
            MessageSource.Viber -> painterResource(R.drawable.ic_viber)
            MessageSource.Telegram -> painterResource(R.drawable.ic_telegram_logo)
            MessageSource.App -> painterResource(R.drawable.ic_app_logo)
        },
        contentDescription = "Source App"
    )
}

@GrodnoRoadsPreview
@Composable
private fun PreviewSourceImage() = GrodnoRoadsM3ThemePreview {
    Column {
        SourceImage(messageSource = MessageSource.App)
        SourceImage(messageSource = MessageSource.Viber)
        SourceImage(messageSource = MessageSource.Telegram)
    }
}