package com.egoriku.grodnoroads.screen.main.ui

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.egoriku.grodnoroads.screen.main.ui.drawer.section.PrivacyPolicySection
import com.egoriku.grodnoroads.screen.main.ui.drawer.section.TelegramSection
import com.egoriku.grodnoroads.screen.main.ui.drawer.section.VersionSection

@Composable
fun DrawerContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val openUrl: (String) -> Unit = {
        CustomTabsIntent.Builder()
            .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
            .build()
            .launchUrl(context, it.toUri())
    }

    Surface(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            TelegramSection(onClick = openUrl)
            VersionSection()
            Divider(modifier = Modifier.fillMaxWidth())
            PrivacyPolicySection(openUrl = openUrl)
        }
    }
}

@Preview(locale = "ru")
@Composable
fun DrawerContentPreview() {
    Surface {
        DrawerContent()
    }
}