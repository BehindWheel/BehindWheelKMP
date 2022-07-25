package com.egoriku.grodnoroads.screen.main.ui.drawer.section

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TelegramSection(onClick: (String) -> Unit) {
    val url = stringResource(R.string.tg_link)

    ListItem(
        modifier = Modifier.clickable { onClick(url) },
        text = {
            Text(text = stringResource(R.string.drawer_telegram_section))
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

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true, locale = "ru")
@Composable
private fun TelegramSectionPreview() {
    GrodnoRoadsTheme {
        TelegramSection(onClick = {})
    }
}