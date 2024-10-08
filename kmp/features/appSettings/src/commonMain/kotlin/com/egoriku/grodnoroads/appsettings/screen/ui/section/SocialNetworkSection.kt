package com.egoriku.grodnoroads.appsettings.screen.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.appsettings.domain.util.STORE_URL
import com.egoriku.grodnoroads.appsettings.domain.util.rememberUrlLauncher
import com.egoriku.grodnoroads.appsettings.domain.util.rememberUrlShare
import com.egoriku.grodnoroads.appsettings.screen.ui.SocialNetwork
import com.egoriku.grodnoroads.compose.resources.Constants.TG_CHANNEL_LINK
import com.egoriku.grodnoroads.compose.resources.Constants.TG_CHAT_LINK
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.social_share_app
import com.egoriku.grodnoroads.compose.resources.social_telegram_channel
import com.egoriku.grodnoroads.compose.resources.social_telegram_chat
import com.egoriku.grodnoroads.foundation.icons.GrodnoRoads
import com.egoriku.grodnoroads.foundation.icons.outlined.Chat
import com.egoriku.grodnoroads.foundation.icons.outlined.Share
import com.egoriku.grodnoroads.foundation.icons.outlined.Telegram
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import org.jetbrains.compose.resources.stringResource

@Composable
fun SocialNetworkSection() {
    val urlLauncher = rememberUrlLauncher()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        SocialNetwork(
            title = stringResource(Res.string.social_telegram_chat),
            onClick = { urlLauncher.openUrl(TG_CHAT_LINK) }
        ) {
            Icon(
                imageVector = GrodnoRoads.Outlined.Chat,
                contentDescription = stringResource(Res.string.social_telegram_chat)
            )
        }
        SocialNetwork(
            title = stringResource(Res.string.social_telegram_channel),
            onClick = { urlLauncher.openUrl(TG_CHANNEL_LINK) }
        ) {
            Icon(
                imageVector = GrodnoRoads.Outlined.Telegram,
                contentDescription = stringResource(Res.string.social_telegram_channel)
            )
        }

        val urlShare = rememberUrlShare()

        SocialNetwork(
            title = stringResource(Res.string.social_share_app),
            onClick = { urlShare.share(STORE_URL) }
        ) {
            Icon(
                imageVector = GrodnoRoads.Outlined.Share,
                contentDescription = stringResource(Res.string.social_share_app)
            )
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun SocialNetworkSectionPreview() = GrodnoRoadsM3ThemePreview {
    SocialNetworkSection()
}