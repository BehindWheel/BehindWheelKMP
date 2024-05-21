package com.egoriku.grodnoroads.guidance.screen.ui.mode.drive.alerts.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.shared.models.MessageSource
import com.egoriku.grodnoroads.shared.resources.MR
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun SourceImage(
    modifier: Modifier = Modifier,
    messageSource: MessageSource
) {
    Image(
        modifier = modifier,
        painter = when (messageSource) {
            MessageSource.Viber -> painterResource(MR.images.nt_ic_viber)
            MessageSource.Telegram -> painterResource(MR.images.nt_ic_telegram)
            MessageSource.App -> painterResource(MR.images.nt_ic_app)
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