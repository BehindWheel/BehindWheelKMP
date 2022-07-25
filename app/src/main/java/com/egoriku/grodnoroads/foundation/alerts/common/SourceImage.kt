package com.egoriku.grodnoroads.foundation.alerts.common

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.screen.map.domain.Source
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme

@Composable
fun SourceImage(modifier: Modifier = Modifier, source: Source) {
    Image(
        modifier = modifier.size(24.dp),
        painter = when (source) {
            Source.Viber -> painterResource(R.drawable.ic_viber)
            Source.Telegram -> painterResource(R.drawable.ic_telegram)
            Source.App -> painterResource(R.drawable.ic_app_logo)
        },
        contentDescription = "Source App"
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun PreviewSourceImage() {
    GrodnoRoadsTheme {
        Column {
            SourceImage(source = Source.App)
            SourceImage(source = Source.Viber)
            SourceImage(source = Source.Telegram)
        }
    }
}