package com.egoriku.grodnoroads.screen.main.ui.drawer

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.egoriku.grodnoroads.foundation.WSpacer

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    navigate: (screen: DrawerNavigationScreen) -> Unit
) {
    val context = LocalContext.current

    val navigationItems = listOf(
        DrawerNavigationScreen.Settings
    )

    Surface(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(navigationItems) { item ->
                    DrawerNavigationItem(
                        contentPadding = PaddingValues(start = 16.dp),
                        text = stringResource(item.resourceId),
                        imageVector = item.imageVector,
                        onClick = {
                            navigate(item)
                        }
                    )
                }
            }
            PrivacyPolicy { url ->
                CustomTabsIntent.Builder()
                    .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
                    .build()
                    .launchUrl(context, url.toUri())
            }
        }
    }
}

@Composable
private fun DrawerNavigationItem(
    contentPadding: PaddingValues = PaddingValues(),
    text: String,
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp)
            .padding(paddingValues = contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = imageVector,
            contentDescription = null
        )
        WSpacer(24.dp)
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(locale = "ru")
@Composable
fun DrawerContentPreview() {
    Surface {
        DrawerContent {}
    }
}