package com.egoriku.grodnoroads.appsettings.screen.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.appsettings.screen.ui.SocialNetwork
import com.egoriku.grodnoroads.appsettings.screen.util.rememberCustomTabIntent
import com.egoriku.grodnoroads.appsettings.screen.util.rememberShareIntent
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsM3ThemePreview
import com.egoriku.grodnoroads.foundation.preview.GrodnoRoadsPreview
import com.egoriku.grodnoroads.resources.R

@Composable
fun SocialNetworkSection() {
    val customTabsIntent = rememberCustomTabIntent()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        val chatUrl = stringResource(R.string.tg_chat_link)
        val channelUrl = stringResource(R.string.tg_channel_link)
        val playStoreUrl = stringResource(R.string.play_store_link)

        SocialNetwork(
            title = stringResource(R.string.social_telegram_chat),
            onClick = { customTabsIntent(chatUrl) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_chat),
                contentDescription = stringResource(R.string.social_telegram_chat)
            )
        }
        SocialNetwork(
            title = stringResource(R.string.social_telegram_channel),
            onClick = { customTabsIntent(channelUrl) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_telegram),
                contentDescription = stringResource(R.string.social_telegram_channel)
            )
        }

        val shareIntent = rememberShareIntent()

        SocialNetwork(
            title = stringResource(R.string.social_share_app),
            onClick = { shareIntent(playStoreUrl) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_share),
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