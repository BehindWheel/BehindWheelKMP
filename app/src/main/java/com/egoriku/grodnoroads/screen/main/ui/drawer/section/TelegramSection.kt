package com.egoriku.grodnoroads.screen.main.ui.drawer.section

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.WSpacer
import com.egoriku.grodnoroads.foundation.icons.RoadIcons
import com.egoriku.grodnoroads.roadicons.IcTelegram
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun TelegramSection(onClick: (String) -> Unit) {
    val url = stringResource(R.string.tg_link)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick(url) })
            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(48.dp),
            imageVector = RoadIcons.IcTelegram,
            contentDescription = null
        )
        WSpacer(24.dp)
        Text(
            text = stringResource(R.string.drawer_telegram_section),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun TelegramSectionPreview() {
    GrodnoRoadsTheme {
        Surface {
            TelegramSection(onClick = {})
        }
    }
}