package com.egoriku.grodnoroads.appsettings.screen.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.appsettings.screen.ui.SocialNetwork
import com.egoriku.grodnoroads.appsettings.screen.util.rememberCustomTabIntent
import com.egoriku.grodnoroads.appsettings.screen.util.rememberShareIntent
import com.egoriku.grodnoroads.compose.resources.Constants.PLAY_STORE_LINK
import com.egoriku.grodnoroads.compose.resources.Constants.TG_CHANNEL_LINK
import com.egoriku.grodnoroads.compose.resources.Constants.TG_CHAT_LINK
import com.egoriku.grodnoroads.compose.resources.Res
import com.egoriku.grodnoroads.compose.resources.ic_chat
import com.egoriku.grodnoroads.compose.resources.ic_share
import com.egoriku.grodnoroads.compose.resources.ic_telegram
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.localization.R
import org.jetbrains.compose.resources.painterResource

@Composable
fun SocialNetworkSection() {
    val customTabsIntent = rememberCustomTabIntent()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        SocialNetwork(
            title = stringResource(R.string.social_telegram_chat),
            onClick = { customTabsIntent(TG_CHAT_LINK) }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_chat),
                contentDescription = stringResource(R.string.social_telegram_chat)
            )
        }
        SocialNetwork(
            title = stringResource(R.string.social_telegram_channel),
            onClick = { customTabsIntent(TG_CHANNEL_LINK) }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_telegram),
                contentDescription = stringResource(R.string.social_telegram_channel)
            )
        }

        val shareIntent = rememberShareIntent()

        SocialNetwork(
            title = stringResource(R.string.social_share_app),
            onClick = { shareIntent(PLAY_STORE_LINK) }
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_share),
                contentDescription = stringResource(R.string.social_share_app)
            )
        }
    }
}

@GrodnoRoadsPreview
@Composable
private fun SocialNetworkSectionPreview() = GrodnoRoadsM3ThemePreview {
    SocialNetworkSection()
}