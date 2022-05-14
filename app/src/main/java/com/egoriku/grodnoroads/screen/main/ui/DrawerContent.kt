package com.egoriku.grodnoroads.screen.main.ui

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.egoriku.grodnoroads.foundation.drawer.DrawerNavigationItem
import com.egoriku.grodnoroads.screen.main.ui.drawer.DrawerNavigationScreen
import com.egoriku.grodnoroads.screen.main.ui.drawer.section.PrivacyPolicySection
import com.egoriku.grodnoroads.screen.main.ui.drawer.section.TelegramSection
import com.egoriku.grodnoroads.screen.main.ui.drawer.section.VersionSection

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    navigate: (screen: DrawerNavigationScreen) -> Unit
) {
    val context = LocalContext.current

    val openUrl: (String) -> Unit = {
        CustomTabsIntent.Builder()
            .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
            .build()
            .launchUrl(context, it.toUri())
    }

    val navigationItems = listOf(
        DrawerNavigationScreen.Settings
    )

    Surface(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                /*items(navigationItems) { item ->
                    DrawerNavigationItem(
                        contentPadding = PaddingValues(start = 16.dp),
                        text = stringResource(item.resourceId),
                        imageVector = item.imageVector,
                        onClick = {
                            navigate(item)
                        }
                    )
                }*/
            }
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
        DrawerContent {}
    }
}