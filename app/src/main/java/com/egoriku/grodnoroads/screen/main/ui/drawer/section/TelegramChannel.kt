package com.egoriku.grodnoroads.screen.main.ui.drawer.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsPreview
import com.egoriku.grodnoroads.foundation.theme.GrodnoRoadsTheme
import com.egoriku.grodnoroads.resources.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TelegramChannel(onClick: (String) -> Unit) {
    val url = stringResource(R.string.tg_channel_link)

    ListItem(
        modifier = Modifier.clickable { onClick(url) },
        text = {
            Text(text = stringResource(R.string.drawer_telegram_channel))
        },
        icon = {
            Image(
                modifier = Modifier.size(28.dp),
                painter = painterResource(R.drawable.ic_telegram),
                contentDescription = null
            )
        }
    )
}

@GrodnoRoadsPreview
@Composable
private fun TelegramSectionPreview() {
    GrodnoRoadsTheme {
        TelegramChannel(onClick = {})
    }
}