package com.egoriku.grodnoroads.appsettings.screen.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.appsettings.domain.util.STORE_URL
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
import com.egoriku.grodnoroads.foundation.preview.PreviewGrodnoRoads
import org.jetbrains.compose.resources.stringResource

@Composable
fun SocialNetworkSection(modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SocialNetwork(
            title = stringResource(Res.string.social_telegram_chat),
            onClick = { uriHandler.openUri(TG_CHAT_LINK) }
        ) {
            Icon(
                imageVector = GrodnoRoads.Outlined.Chat,
                contentDescription = stringResource(Res.string.social_telegram_chat)
            )
        }
        SocialNetwork(
            title = stringResource(Res.string.social_telegram_channel),
            onClick = { uriHandler.openUri(TG_CHANNEL_LINK) }
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

@PreviewGrodnoRoads
@Composable
private fun SocialNetworkSectionPreview() = GrodnoRoadsM3ThemePreview {
    SocialNetworkSection()
}
